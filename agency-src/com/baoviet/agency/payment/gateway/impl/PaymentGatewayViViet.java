package com.baoviet.agency.payment.gateway.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.config.GateWayViVietConfig;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.payment.common.Constants;
import com.baoviet.agency.payment.common.PaymentResponseType;
import com.baoviet.agency.payment.common.PaymentStatus;
import com.baoviet.agency.payment.common.PaymentType;
import com.baoviet.agency.payment.dto.PaymentResult;
import com.baoviet.agency.payment.dto.PaymentResultVnPay;
import com.baoviet.agency.payment.gateway.AbstractPaymentGateway;
import com.baoviet.agency.utils.AgencyUtils;
import com.baoviet.agency.web.rest.vm.PaymentProcessRequestVM;

@Service
public class PaymentGatewayViViet extends AbstractPaymentGateway {
	private final Logger log = LoggerFactory.getLogger(PaymentGatewayViViet.class);

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	@Autowired
	private GateWayViVietConfig config;

	@Override
	public PaymentResult processPayment(AgencyDTO currentAgency, PaymentProcessRequestVM param,
			List<Agreement> agreements) {
		log.info("START processPayment ViViet");
		PaymentResult result = new PaymentResult();

		// Create new pay action
		PayAction payAction = new PayAction();

		PaymentResponseType paymentResponseType = super.processPayment(config, payAction, currentAgency, param,
				agreements);

		log.info("paymentResponseType from parent: " + paymentResponseType.name());
		if (paymentResponseType != PaymentResponseType.SUCCESS) {
			result.setResponseType(paymentResponseType);
			return result;
		}
		
		String orderNo = getOrderNo(agreements);
		String orderDesc = getOrderInfo(currentAgency, agreements);
		
		// Build redirectUrl
		StringBuffer redirectUrl = new StringBuffer(config.getServiceUrl());
		redirectUrl.append("?");
		try {
			redirectUrl.append("version=");
			redirectUrl.append(URLEncoder.encode(config.getVersion(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("locale=");
			redirectUrl.append("vn");
			redirectUrl.append("&");
			redirectUrl.append("merchant_site=");
			redirectUrl.append(URLEncoder.encode(config.getMerchantSiteId(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("access_code=");
			redirectUrl.append(URLEncoder.encode(config.getAccessCode(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("return_url=");
			redirectUrl.append(URLEncoder.encode(config.getReturnUrl(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("account_type=");
			redirectUrl.append("VV");
			redirectUrl.append("&");
			redirectUrl.append("merch_txn_ref=");
			redirectUrl.append(URLEncoder.encode(payAction.getMciAddId(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("order_no=");
			redirectUrl.append(URLEncoder.encode(orderNo, "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("order_desc=");
			redirectUrl.append(URLEncoder.encode(orderDesc, "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("total_amount=");
			redirectUrl.append(URLEncoder.encode(String.valueOf(param.getPaymentFee().longValue()*100), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("client_ip=");
			redirectUrl.append(URLEncoder.encode(config.getClientIp(), "UTF-8"));

			Map<String, String> parameters = new LinkedHashMap<>();
			parameters.put(Constants.VIVIET_PARAM_ACCESS_CODE, config.getAccessCode());
			parameters.put(Constants.VIVIET_PARAM_ACCOUNT_TYPE, "VV");
			parameters.put(Constants.VIVIET_PARAM_CLIENT_IP, config.getClientIp());
			parameters.put(Constants.VIVIET_PARAM_LOCALE, "vn");
			parameters.put(Constants.VIVIET_PARAM_MERCH_TXN_REF, payAction.getMciAddId());
			parameters.put(Constants.VIVIET_PARAM_MERCHANT_SITE, config.getMerchantSiteId());
			parameters.put(Constants.VIVIET_PARAM_ORDER_DESC, orderDesc);
			parameters.put(Constants.VIVIET_PARAM_ORDER_NO, orderNo);
			parameters.put(Constants.VIVIET_PARAM_RETURN_URL, config.getReturnUrl());
			parameters.put(Constants.VIVIET_PARAM_TOTAL_AMOUNT, String.valueOf(param.getPaymentFee().longValue()*100));
			parameters.put(Constants.VIVIET_PARAM_VERSION, config.getVersion());
			
			String secureHash = getSecureHash(createValueFormated(parameters));
			redirectUrl.append("&");
			redirectUrl.append("secure_hash=");
			redirectUrl.append(secureHash);
		} catch (Exception e) {
			log.info("UnsupportedEncodingException: " + e.getMessage());
			result.setResponseType(PaymentResponseType.ERROR);
			return result;
		}

		// Insert sale code
		insertSaleCode(payAction, redirectUrl.toString(), param.getBankCode(), PaymentType.ViViet);

		log.info("END processPayment ViViet");
		result.setResponseType(PaymentResponseType.SUCCESS);
		result.setRedirectUrl(redirectUrl.toString());
		return result;
	}

	private String calculateRFC2104HMAC(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.US_ASCII), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
	}
	
    /**
     * Hàm thực hiện tạo mã Hash để check tính toàn vẹn của dữ liệu
     * 
     * @param messages
     * @return
     */
    private String getSecureHash(String message) {
        return hash(config.getSercuritySecret() + "|" + message, "SHA-256");
    }

    /**
     * Hàm hash dữ liệu
     * 
     * @param message
     *            dữ liệu hash
     * @param algorithm
     *            thuật toán hash
     * @return
     */
    public String hash(String message, String algorithm) {
        MessageDigest md;
        byte byteData[];
        try {
            md = MessageDigest.getInstance(algorithm);
            md.update(message.getBytes("UTF-8"));
            byteData = md.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }

        return sb.toString();
    }

    /**
     * Hàm tạo chuỗi String có value ngăn cách nhau bằng '|' và sắp xếp theo thứ
     * tự tên tham số
     * 
     * @param parameters
     * @return
     */
    // secret_key|access_code|client_ip|merch_txn_ref|merchant_site|order_desc|order_no|return_url|total_amount|version
    private String createValueFormated(Map<String, String> parameters) {
        List<String> keyList = new ArrayList<String>(parameters.keySet());
        Collections.sort(keyList);
        StringBuilder valueFormated = new StringBuilder();
        for (String key : keyList) {
            valueFormated.append(parameters.get(key));
            valueFormated.append("|");
        }
        return valueFormated.toString()
                .substring(0, valueFormated.length() - 1);
    }

    /**
     * Hàm valid response từ Ví Việt
     * 
     * @param parameters
     *            Tham số trả về
     * @return
     */
    private boolean validResponseUrl(Map<String, String> parameters) {
        String secure_hash_response = parameters.get("secure_hash");
        String secureHash_client = getSecureHash(createValueFormated(parameters));
        return secureHash_client.equals(secure_hash_response);

    }

	@Override
	public PaymentResult processReturn(Map<String, String> paramMap, String vnpTmnCode) {
		PaymentResult result = new PaymentResult();
		
		if (paramMap.get(Constants.VIVIET_PARAM_RESPONSE_CODE) != null
				&& !paramMap.get(Constants.VIVIET_PARAM_RESPONSE_CODE).isEmpty()) {
			// Cap nhat trang thai
			if (StringUtils.isNotEmpty(paramMap.get(Constants.VIVIET_PARAM_MERCH_TXN_REF))) {
				PayAction payAction = payActionRepository.findByMciAddId(paramMap.get(Constants.VIVIET_PARAM_MERCH_TXN_REF));
				
				if (payAction != null && payAction.getPayEndDate() == null) {
					if (paramMap.get(Constants.VIVIET_PARAM_RESPONSE_CODE) != null && !paramMap.get(Constants.VIVIET_PARAM_RESPONSE_CODE).isEmpty()) {
						payAction.setPayEndDate(new Date());

						if (paramMap.get(Constants.VIVIET_PARAM_TRANSACTION_NO) != null
								&& !paramMap.get(Constants.VIVIET_PARAM_TRANSACTION_NO).isEmpty()) {
							String transactionNo = paramMap.get(Constants.VIVIET_PARAM_TRANSACTION_NO);
							payAction.setTransactionId(transactionNo);
							payAction.setPayLog("\\n[Response]: " + paramMap);

							if (paramMap.get(Constants.VIVIET_PARAM_RESPONSE_CODE).equals(Constants.PAYMENT_VIVIET_STATUS_SUCCESS)) {
								payAction.setStatus(91);
								updatePaymentResult(PaymentStatus.SUCCESSFUL, payAction.getMciAddId(), transactionNo, payAction);
								result.setMciAddId(payAction.getMciAddId());
								result.setPolicyNumber(payAction.getPolicyNumbers());
								result.setResponseType(PaymentResponseType.SUCCESS);
							} else {
								payAction.setStatus(90);
								updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), transactionNo, payAction);
								result.setMciAddId(payAction.getMciAddId());
								result.setPolicyNumber(payAction.getPolicyNumbers());
								result.setResponseType(PaymentResponseType.ERROR);
							}
						} else {
							payAction.setStatus(90);
							updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), "", payAction);
						}
						payActionRepository.save(payAction);
					}
				} else if (payAction != null && payAction.getPayEndDate() != null) {
					result.setRspCode("02");
					result.setMessage("Order already confirmed");
					result.setMciAddId(payAction.getMciAddId());
					result.setPolicyNumber(payAction.getPolicyNumbers());	
					result.setResponseType(PaymentResponseType.ERROR);
					return result;
				} else {
					result.setRspCode("01");
					result.setMessage("Order not found");
					result.setResponseType(PaymentResponseType.ERROR);
				}
			} else {
				result.setRspCode("01");
				result.setMessage("Order not found");
				result.setResponseType(PaymentResponseType.ERROR);
			}
		} else {
			result.setRspCode("99");
			result.setResponseType(PaymentResponseType.ERROR);
		}
		
		return result;
	}
	
	private String getOrderInfo(AgencyDTO currentAgency, List<Agreement> agreements) {
		String vnpOrderInfo = "";
		String gycbhNumber = "";
		String agencyName = "";
		for (Agreement agreement : agreements) {
			gycbhNumber += agreement.getGycbhNumber() + ";";
		}
		vnpOrderInfo = gycbhNumber.substring(0, gycbhNumber.length() -1);
		if (StringUtils.isNotEmpty(currentAgency.getTen())) {
			agencyName = AgencyUtils.toKhongDau(currentAgency.getTen());
			vnpOrderInfo += "_" + agencyName;
		}
		if (StringUtils.isNotEmpty(currentAgency.getDienThoai())) {
			vnpOrderInfo += "_" + currentAgency.getDienThoai();
		}
		vnpOrderInfo += "_" + currentAgency.getEmail();
		if (vnpOrderInfo.length() > 255) {
			vnpOrderInfo = vnpOrderInfo.substring(0, 255);
		}
		return vnpOrderInfo;	
	}
	
	private String getOrderNo(List<Agreement> agreements) {
		String vnpOrderInfo = "";
		String gycbhNumber = "";
		for (Agreement agreement : agreements) {
			gycbhNumber += agreement.getGycbhNumber() + ";";
		}
		vnpOrderInfo = gycbhNumber.substring(0, gycbhNumber.length() -1);
		return vnpOrderInfo;	
	}
	
	@Override
	public boolean updateStatus(String transRef, String responseString) throws AgencyBusinessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PaymentResultVnPay updateStatusWebVnPay(String transRef, String responseString)
			throws AgencyBusinessException {
		// TODO Auto-generated method stub
		return null;
	}
}
