package com.baoviet.agency.payment.gateway.impl;

import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.baoviet.agency.bean.FileContentDTO;
import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.config.GateWayVnPayConfig;
import com.baoviet.agency.domain.AdminBuSeal;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.Attachment;
import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.payment.common.Constants;
import com.baoviet.agency.payment.common.PaymentResponseType;
import com.baoviet.agency.payment.common.PaymentStatus;
import com.baoviet.agency.payment.common.PaymentType;
import com.baoviet.agency.payment.common.VnPayOrderStatus;
import com.baoviet.agency.payment.dto.PaymentResult;
import com.baoviet.agency.payment.dto.PaymentResultVnPay;
import com.baoviet.agency.payment.dto.VnPayOrderStatusResponse;
import com.baoviet.agency.payment.gateway.AbstractPaymentGateway;
import com.baoviet.agency.repository.AdminBuSealRepository;
import com.baoviet.agency.utils.AgencyUtils;
import com.baoviet.agency.web.rest.vm.PaymentProcessRequestVM;

import sun.misc.BASE64Encoder;

@Service
public class PaymentGatewayVnPay extends AbstractPaymentGateway {
	private final Logger log = LoggerFactory.getLogger(PaymentGatewayVnPay.class);

	@Autowired
	private GateWayVnPayConfig config;
	
	@Autowired
	private AdminBuSealRepository adminBuSealRepository;

	@Override
	public PaymentResult processPayment(AgencyDTO currentAgency, PaymentProcessRequestVM param,
			List<Agreement> agreements) {
		// Multiply 100 paymentFee
		param.setPaymentFee(param.getPaymentFee() * 100);
		
		log.info("START processPayment VnPay");
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

		// Build redirectUrl
		Date now = new Date();
		StringBuffer redirectUrl = new StringBuffer(config.getServiceUrl());
		redirectUrl.append("?");
		try {
			String vnpTmnCode = getVnpTmnCode(agreements.get(0).getBaovietCompanyId());
			
			String vnpOrderInfo = getVnpOrderInfo(currentAgency, agreements);
			
			redirectUrl.append("vnp_Amount=");
			redirectUrl.append(URLEncoder.encode(String.valueOf(param.getPaymentFee().longValue()), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_BankCode=");
			redirectUrl.append(URLEncoder.encode(param.getBankCode(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_Command=");
			redirectUrl.append(URLEncoder.encode(config.getCommand(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_CreateDate=");
			redirectUrl.append(URLEncoder.encode(new SimpleDateFormat("yyyyMMddHHmmss").format(now), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_CurrCode=");
			redirectUrl.append(URLEncoder.encode(config.getCurrency(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_IpAddr=");
			redirectUrl.append(URLEncoder.encode("::1", "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_Locale=");
			redirectUrl.append(URLEncoder.encode("vn", "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_OrderInfo=");
			redirectUrl.append(URLEncoder.encode(vnpOrderInfo, "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_OrderType=");
			redirectUrl.append(URLEncoder.encode(param.getBankCode(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_ReturnUrl=");
			redirectUrl.append(URLEncoder.encode(config.getReturnUrl(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_TmnCode=");
			redirectUrl.append(URLEncoder.encode(vnpTmnCode, "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_TxnRef=");
			redirectUrl.append(URLEncoder.encode(payAction.getMciAddId(), "UTF-8"));
			redirectUrl.append("&");
			redirectUrl.append("vnp_Version=");
			redirectUrl.append(URLEncoder.encode(config.getVersion(), "UTF-8"));
			redirectUrl.append("&");

			String secureHash = getSecureHash(payAction, param, now, vnpTmnCode, vnpOrderInfo);
			redirectUrl.append("vnp_SecureHash=");
			redirectUrl.append(secureHash);
		} catch (Exception e) {
			log.info("UnsupportedEncodingException: " + e.getMessage());
			result.setResponseType(PaymentResponseType.ERROR);
			return result;
		}

		// Insert sale code
		insertSaleCode(payAction, redirectUrl.toString(), param.getBankCode(), PaymentType.VnPay);

		log.info("END processPayment VnPay");
		result.setResponseType(PaymentResponseType.SUCCESS);
		result.setRedirectUrl(redirectUrl.toString());
		return result;
	}

	private String getSecureHash(PayAction payAction, PaymentProcessRequestVM param, Date now, String vnpTmnCode, String vnpOrderInfo) {
		StringBuffer requestData = new StringBuffer();
		try {
			requestData.append("vnp_Amount=");
			requestData.append(String.valueOf(param.getPaymentFee().longValue()));
			requestData.append("&");
			requestData.append("vnp_BankCode=");
			requestData.append(param.getBankCode());
			requestData.append("&");
			requestData.append("vnp_Command=");
			requestData.append(config.getCommand());
			requestData.append("&");
			requestData.append("vnp_CreateDate=");
			requestData.append(new SimpleDateFormat("yyyyMMddHHmmss").format(now));
			requestData.append("&");
			requestData.append("vnp_CurrCode=");
			requestData.append(config.getCurrency());
			requestData.append("&");
			requestData.append("vnp_IpAddr=");
			requestData.append("::1");
			requestData.append("&");
			requestData.append("vnp_Locale=");
			requestData.append("vn");
			requestData.append("&");
			requestData.append("vnp_OrderInfo=");
			requestData.append(vnpOrderInfo);
			requestData.append("&");
			requestData.append("vnp_OrderType=");
			requestData.append(param.getBankCode());
			requestData.append("&");
			requestData.append("vnp_ReturnUrl=");
			requestData.append(config.getReturnUrl());
			requestData.append("&");
			requestData.append("vnp_TmnCode=");
			requestData.append(vnpTmnCode);
			requestData.append("&");
			requestData.append("vnp_TxnRef=");
			requestData.append(payAction.getMciAddId());
			requestData.append("&");
			requestData.append("vnp_Version=");
			requestData.append(config.getVersion());

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(config.getHashSecret() + requestData.toString()));
			String secureHash = String.format("%032x", new BigInteger(1, md5.digest()));
			return secureHash;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

	private String getSecureHash(Map<String, String> paramMap, String vnpTmnCode) {
		StringBuffer requestData = new StringBuffer();
		try {
			requestData.append("vnp_Command=");
			requestData.append("querydr");
			requestData.append("&");
			requestData.append("vnp_CreateDate=");
			requestData.append(paramMap.get(Constants.VNPAY_PARAM_CREATE_DATE));
			requestData.append("&");
			requestData.append("vnp_IpAddr=");
			requestData.append("::1");
			requestData.append("&");
			requestData.append("vnp_Merchant=");
			requestData.append("VNPAY");
			requestData.append("&");
			requestData.append("vnp_OrderInfo=");
			requestData
					.append("Call api lay thong tin don hang voi ma = " + paramMap.get(Constants.VNPAY_PARAM_TXN_REF));
			requestData.append("&");
			requestData.append("vnp_TmnCode=");
			requestData.append(vnpTmnCode);
			requestData.append("&");
			requestData.append("vnp_TransDate=");
			requestData.append(paramMap.get(Constants.VNPAY_PARAM_TRANS_DATE));
			requestData.append("&");
			requestData.append("vnp_TxnRef=");
			requestData.append(paramMap.get(Constants.VNPAY_PARAM_TXN_REF));
			requestData.append("&");
			requestData.append("vnp_Version=");
			requestData.append(config.getVersion());

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(config.getHashSecret() + requestData.toString()));
			String secureHash = String.format("%032x", new BigInteger(1, md5.digest()));
			return secureHash;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

	@Override
	public PaymentResult processReturn(Map<String, String> paramMap, String vnpTmnCode) throws AgencyBusinessException {
		String redirectUrl = applicationProperties.getPaymentReturnPage();
		PaymentResult result = new PaymentResult();
		if (validateSignature(paramMap)) {
			// Cap nhat trang thai
			PayAction payAction = payActionRepository.findByMciAddId(paramMap.get(Constants.VNPAY_PARAM_TXN_REF));
			if (payAction != null && payAction.getPayEndDate() == null) {
				if (paramMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE).equals(Constants.PAYMENT_VNPAY_STATUS_SUCCESS)) {
					boolean orderResult = processOrder(payAction, paramMap);	// duclm add 21/02
					
					if (!orderResult) {
						result.setRspCode("99");
						if (payAction != null) {
							result.setMciAddId(payAction.getMciAddId());
							result.setPolicyNumber(payAction.getPolicyNumbers());	
						}
						result.setResponseType(PaymentResponseType.ERROR); // duclm add 21/02	
					} else {
						result.setRspCode("00");
						result.setMessage("Confirm Success");
						if (payAction != null) {
							result.setMciAddId(payAction.getMciAddId());
							result.setPolicyNumber(payAction.getPolicyNumbers());	
						}
						result.setResponseType(PaymentResponseType.SUCCESS); // duclm add 21/02	
					}
				} else {
					result.setRspCode("99");
					result.setMciAddId(payAction.getMciAddId());
					result.setPolicyNumber(payAction.getPolicyNumbers());	
					result.setResponseType(PaymentResponseType.ERROR);
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
			PayAction payAction = payActionRepository.findByMciAddId(paramMap.get(Constants.VNPAY_PARAM_TXN_REF));
			if (payAction != null) {
				result.setMciAddId(payAction.getMciAddId());
				result.setPolicyNumber(payAction.getPolicyNumbers());
			}
			result.setRspCode("97");
			result.setMessage("Chu ky khong hop le");
			result.setResponseType(PaymentResponseType.ERROR);
			log.info("Update PayAction Chu ky khong hop le");
		}
		return result;
	}
	
//	backup 21/02
//	@Override
//	public PaymentResult processReturn(Map<String, String> paramMap, String vnpTmnCode) throws AgencyBusinessException {
//		String redirectUrl = applicationProperties.getPaymentReturnPage();
//		PaymentResult result = new PaymentResult();
//		if (validateSignature(paramMap)) {
//			// Cap nhat trang thai
//			PayAction payAction = payActionRepository.findByMciAddId(paramMap.get(Constants.VNPAY_PARAM_TXN_REF));
//			if (payAction != null && payAction.getPayEndDate() == null) {
//				// NamNH : 14/1/2019
//				processOrder(result, payAction, paramMap, vnpTmnCode);
//				result.setResponseType(PaymentResponseType.NEED_VALIDATE_TRANSACTION);
//		
//				if (paramMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE).equals(Constants.PAYMENT_VNPAY_STATUS_SUCCESS)) {
//					result.setRspCode("00");
//					result.setMessage("Confirm Success");
//					if (payAction != null) {
//						result.setMciAddId(payAction.getMciAddId());
//						result.setPolicyNumber(payAction.getPolicyNumbers());	
//					}
//					result.setResponseType(PaymentResponseType.NEED_VALIDATE_TRANSACTION);
//				} else {
//					result.setRspCode("99");
//					result.setMciAddId(payAction.getMciAddId());
//					result.setPolicyNumber(payAction.getPolicyNumbers());	
//					result.setResponseType(PaymentResponseType.ERROR);
//				}
//				
//			} else if (payAction != null && payAction.getPayEndDate() != null) {
//				result.setRspCode("02");
//				result.setMessage("Order already confirmed");
//				result.setMciAddId(payAction.getMciAddId());
//				result.setPolicyNumber(payAction.getPolicyNumbers());	
//				result.setResponseType(PaymentResponseType.ERROR);
//				return result;
//			} else {
//				result.setRspCode("01");
//				result.setMessage("Order not found");
//				result.setResponseType(PaymentResponseType.ERROR);
//			}
//		} else {
//			PayAction payAction = payActionRepository.findByMciAddId(paramMap.get(Constants.VNPAY_PARAM_TXN_REF));
//			if (payAction != null) {
//				result.setMciAddId(payAction.getMciAddId());
//				result.setPolicyNumber(payAction.getPolicyNumbers());
//			}
//			result.setRspCode("97");
//			result.setMessage("Chu ky khong hop le");
//			result.setResponseType(PaymentResponseType.ERROR);
//			log.info("Update PayAction Chu ky khong hop le");
//		}
//		return result;
//	}
	
	@Override
	public boolean updateStatus(String transRef, String responseString) throws AgencyBusinessException {
		PayAction payAction = payActionRepository.findByMciAddId(transRef);
		
		String[] dataArr = responseString.split("&");
		Map<String, String> dataMap = new HashMap<>();
		for (String data : dataArr) {
			String[] dataPair = data.split("=");
			dataMap.put(dataPair[0], dataPair[1]);
		}

		if (dataMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE) != null
				&& !dataMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE).isEmpty()) {
			
			String transactionStatus = dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_STATUS);
			String transactionNo = dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO);
			payAction.setPayEndDate(new Date());

			if (dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_STATUS) != null
					&& !dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_STATUS).isEmpty()
					&& dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO) != null
					&& !dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO).isEmpty()) {
				payAction.setTransactionId(dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO));
				payAction.setPayLog("\\n[Response]: " + responseString);

				if (transactionStatus.equals(Constants.PAYMENT_VNPAY_STATUS_SUCCESS)) {
					payAction.setStatus(91);
					updatePaymentResult(PaymentStatus.SUCCESSFUL, payAction.getMciAddId(), transactionNo,
							payAction);
				} else {
					payAction.setStatus(90);
					updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), transactionNo, payAction);
				}
			} else {
				payAction.setStatus(90);
				updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), "", payAction);
			}

			payActionRepository.save(payAction);
		}
		
		return true;
	}
	
	@Override
	public PaymentResultVnPay updateStatusWebVnPay(String transRef, String responseString) throws AgencyBusinessException {
		PaymentResultVnPay result = new PaymentResultVnPay();
		
		PayAction payAction = payActionRepository.findByMciAddId(transRef);
		
		String[] dataArr = responseString.split("&");
		Map<String, String> dataMap = new HashMap<>();
		for (String data : dataArr) {
			String[] dataPair = data.split("=");
			dataMap.put(dataPair[0], dataPair[1]);
		}
		
		if (payAction != null && payAction.getPayEndDate() == null) {
			if (dataMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE).equals(Constants.PAYMENT_VNPAY_STATUS_SUCCESS)) {
				result.setRspCode("00");
				result.setMessage("Confirm Success");
			} else {
				result.setRspCode("99");
			}
		} else if (payAction != null && payAction.getPayEndDate() != null) {
			result.setRspCode("02");
			result.setMessage("Order already confirmed");
			return result;
		} else {
			result.setRspCode("01");
			result.setMessage("Order not found");
			return result;
		}

		if (dataMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE) != null && !dataMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE).isEmpty()) {
			
			String transactionStatus = dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_STATUS);
			String transactionNo = dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO);
			payAction.setPayEndDate(new Date());

			if (dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_STATUS) != null
					&& !dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_STATUS).isEmpty()
					&& dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO) != null
					&& !dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO).isEmpty()) {
				payAction.setTransactionId(dataMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO));
				payAction.setPayLog("\\n[Response]: " + responseString);

				if (transactionStatus.equals(Constants.PAYMENT_VNPAY_STATUS_SUCCESS)) {
					payAction.setStatus(91);
					updatePaymentResult(PaymentStatus.SUCCESSFUL, payAction.getMciAddId(), transactionNo, payAction);
				} else {
					payAction.setStatus(90);
					updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), transactionNo, payAction);
					result.setRspCode("99");
					result.setMessage("");
				}
			} else {
				payAction.setStatus(90);
				updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), "", payAction);
				result.setRspCode("99");
				result.setMessage("");
			}

			payActionRepository.save(payAction);
		}
		
		return result;
	}

//	public VnPayOrderStatusResponse processVnPayOrder(Map<String, String> paramMap, String vnpTmnCode) {
//		VnPayOrderStatusResponse orderStatusResponse = new VnPayOrderStatusResponse();
//		if (validateSignature(paramMap)) {
//			// Cap nhat trang thai
//			PayAction payAction = payActionRepository.findByMciAddId(paramMap.get(Constants.VNPAY_PARAM_TXN_REF));
//			if (payAction == null) {
//				orderStatusResponse.setRspCode(VnPayOrderStatus.ORDER_NOT_FOUND.getCode());
//				orderStatusResponse.setMessage(VnPayOrderStatus.ORDER_NOT_FOUND.getMessage());
//			} else {
//				if (payAction.getPayEndDate() != null) {
//					orderStatusResponse.setRspCode(VnPayOrderStatus.ORDER_ALREADY_CONFIRMED.getCode());
//					orderStatusResponse.setMessage(VnPayOrderStatus.ORDER_ALREADY_CONFIRMED.getMessage());
//				} else {
//					boolean orderResult = processOrder(payAction, paramMap, vnpTmnCode);
//					if (!orderResult) {
//						orderStatusResponse.setRspCode(VnPayOrderStatus.SUCCESSFUL.getCode());
//						orderStatusResponse.setMessage(VnPayOrderStatus.SUCCESSFUL.getMessage());
//					}
//				}
//			}
//
//			if (paramMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE).equals(Constants.PAYMENT_VNPAY_STATUS_SUCCESS)) {
//				log.info("Thanh toán thành công, OrderId=" + paramMap.get(Constants.VNPAY_PARAM_TXN_REF)
//						+ ", VNPAY TranId=" + paramMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO));
//			} else {
//				log.info("Thanh toán lỗi, OrderId=" + paramMap.get(Constants.VNPAY_PARAM_TXN_REF) + ", VNPAY TranId="
//						+ paramMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO));
//			}
//		} else {
//			orderStatusResponse.setRspCode(VnPayOrderStatus.INVALID_SIGNATURE.getCode());
//			orderStatusResponse.setMessage(VnPayOrderStatus.INVALID_SIGNATURE.getMessage());
//		}
//		return orderStatusResponse;
//	}

	private void processOrder(PaymentResult result, PayAction payAction, Map<String, String> paramMap, String vnpTmnCode) {
		Date now = new Date();
		String createdDate = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
		String transDate = new SimpleDateFormat("yyyyMMddHHmmss").format(payAction.getPayStartDate());
		paramMap.put(Constants.VNPAY_PARAM_CREATE_DATE, createdDate);
		paramMap.put(Constants.VNPAY_PARAM_TRANS_DATE, transDate);

		StringBuffer checkOrderUrl = new StringBuffer(config.getQuerydr());
		checkOrderUrl.append("?");
		String joinChar = "@@@";
		try {
			checkOrderUrl.append("vnp_Command=");
			checkOrderUrl.append(URLEncoder.encode("querydr", "UTF-8"));
			checkOrderUrl.append(joinChar);
			checkOrderUrl.append("vnp_CreateDate=");
			checkOrderUrl.append(createdDate);
			checkOrderUrl.append(joinChar);
			checkOrderUrl.append("vnp_IpAddr=");
			checkOrderUrl.append(URLEncoder.encode("::1", "UTF-8"));
			checkOrderUrl.append(joinChar);
			checkOrderUrl.append("vnp_Merchant=");
			checkOrderUrl.append(URLEncoder.encode("VNPAY", "UTF-8"));
			checkOrderUrl.append(joinChar);
			checkOrderUrl.append("vnp_OrderInfo=");
			checkOrderUrl.append(URLEncoder.encode(
					"Call api lay thong tin don hang voi ma = " + paramMap.get(Constants.VNPAY_PARAM_TXN_REF),
					"UTF-8"));
			checkOrderUrl.append(joinChar);
			checkOrderUrl.append("vnp_TmnCode=");
			checkOrderUrl.append(URLEncoder.encode(vnpTmnCode, "UTF-8"));
			checkOrderUrl.append(joinChar);
			checkOrderUrl.append("vnp_TransDate=");
			checkOrderUrl.append(transDate);
			checkOrderUrl.append(joinChar);
			checkOrderUrl.append("vnp_TxnRef=");
			checkOrderUrl.append(URLEncoder.encode(paramMap.get(Constants.VNPAY_PARAM_TXN_REF), "UTF-8"));
			checkOrderUrl.append(joinChar);
			checkOrderUrl.append("vnp_Version=");
			checkOrderUrl.append(URLEncoder.encode(config.getVersion(), "UTF-8"));
			checkOrderUrl.append(joinChar);

			String secureHash = getSecureHash(paramMap, vnpTmnCode);
			checkOrderUrl.append("vnp_SecureHash=");
			checkOrderUrl.append(secureHash);
			
			// Base64
			result.setLinkValidateTransaction(checkOrderUrl.toString());
			result.setTransRef(URLEncoder.encode(paramMap.get(Constants.VNPAY_PARAM_TXN_REF), "UTF-8"));
			if (payAction != null) {
				result.setPolicyNumber(payAction.getPolicyNumbers());	
			}
			

		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return;
		}
	}

	private boolean validateSignature(Map<String, String> paramMap) {
		StringBuffer requestData = new StringBuffer();
		for (Entry<String, String> entry : paramMap.entrySet()) {
			if (!entry.getKey().equals(Constants.VNPAY_PARAM_SECURE_HASH)) {
				if (StringUtils.isNotEmpty(entry.getValue())) {
					requestData.append(entry.getKey());
					requestData.append("=");
					requestData.append(entry.getValue());
					requestData.append("&");	
				}
			}	
		}
		try {
			if (!StringUtils.isEmpty(requestData)) {
				String data = requestData.substring(0, requestData.length() - 1);
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				md5.update(StandardCharsets.UTF_8.encode(config.getHashSecret() + data));
				String myChecksum = String.format("%032x", new BigInteger(1, md5.digest()));

				
				log.info("validateSignature , hashSecret {}"+ config.getHashSecret());
				log.info("validateSignature , requestData {}"+ data);
				log.info("validateSignature , myChecksum {}"+ myChecksum);
				
				if (myChecksum.equalsIgnoreCase(paramMap.get(Constants.VNPAY_PARAM_SECURE_HASH))) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String getVnpTmnCode(String baovietCompanyId) throws AgencyBusinessException{
//		return "BAOVIET1";
		if (StringUtils.isNotEmpty(baovietCompanyId)) {
			AdminBuSeal adminBuSeal = adminBuSealRepository.findOne(baovietCompanyId);
			if (adminBuSeal != null) {
				return adminBuSeal.getTerminalCode();
			} else {
				throw new AgencyBusinessException("baovietCompanyId", ErrorCode.PAYMENT_ERROR, "Không tồn tại công ty");
			}
		} else {
			throw new AgencyBusinessException("baovietCompanyId", ErrorCode.PAYMENT_ERROR, "Không tồn tại công ty");
		}
	}
	
	private String getVnpOrderInfo(AgencyDTO currentAgency,	List<Agreement> agreements) {
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

	// old
	private boolean processOrder(PayAction payAction, Map<String, String> paramMap) {
		if (paramMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE) != null
				&& !paramMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE).isEmpty()) {
			
			String transactionStatus = paramMap.get(Constants.VNPAY_PARAM_RESPONSE_CODE);
			String transactionNo = paramMap.get(Constants.VNPAY_PARAM_TRANSACTION_NO);
			payAction.setPayEndDate(new Date());

			if (StringUtils.isNotEmpty(transactionNo)) {
				payAction.setPayLog("\\n[Response]: " + paramMap);

				if (transactionStatus.equals(Constants.PAYMENT_VNPAY_STATUS_SUCCESS)) {
					payAction.setStatus(91);
					updatePaymentResult(PaymentStatus.SUCCESSFUL, payAction.getMciAddId(), transactionNo, payAction);
				} else {
					payAction.setStatus(90);
					updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), transactionNo, payAction);
				}
			} else {
				payAction.setStatus(90);
				updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), "", payAction);
			}
			payActionRepository.save(payAction);
		}
		return true;
	}
}
