package com.baoviet.agency.payment.gateway.impl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.baoviet.agency.config.GateWayMomoConfig;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.payment.common.Constants;
import com.baoviet.agency.payment.common.PaymentResponseType;
import com.baoviet.agency.payment.common.PaymentStatus;
import com.baoviet.agency.payment.common.PaymentType;
import com.baoviet.agency.payment.dto.MomoRequestDTO;
import com.baoviet.agency.payment.dto.MomoResponseDTO;
import com.baoviet.agency.payment.dto.PaymentResult;
import com.baoviet.agency.payment.gateway.AbstractPaymentGateway;
import com.baoviet.agency.web.rest.vm.PaymentProcessRequestVM;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentGatewayMomo extends AbstractPaymentGateway {
	private final Logger log = LoggerFactory.getLogger(PaymentGatewayMomo.class);

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA256";

	@Autowired
	private GateWayMomoConfig config;

	@Override
	public PaymentResult processPayment(AgencyDTO currentAgency, PaymentProcessRequestVM param,
			List<Agreement> agreements) {
		log.info("START processPayment PaymentGatewayMomo");
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
		MomoRequestDTO momoRequest = getMomoRequest(payAction, param);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		MomoResponseDTO momoResponseDTO = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(momoRequest), headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(config.getServiceUrl(), HttpMethod.POST, entity, String.class);
			momoResponseDTO = mapper.readValue(responseEntity.getBody(), MomoResponseDTO.class);
		} catch (Exception e) {
			log.info("Exception: " + e.getMessage());
			result.setResponseType(PaymentResponseType.ERROR);
			return result;
		}  

		if(momoResponseDTO != null) {
			// Insert sale code
			insertSaleCode(payAction, momoResponseDTO.getPayUrl(), param.getBankCode(), PaymentType.Momo);
		}

		log.info("END processPayment PaymentGatewayMomo");
		result.setResponseType(PaymentResponseType.SUCCESS);
		result.setRedirectUrl(momoResponseDTO.getPayUrl());
		return result;
	}

	private MomoRequestDTO getMomoRequest(PayAction payAction, PaymentProcessRequestVM param) {
		StringBuffer requestData = new StringBuffer();
		try {
			String requestId = UUID.randomUUID().toString();
			requestData.append("partnerCode=");
			requestData.append(config.getPartnerCode());
			requestData.append("&");
			requestData.append("accessKey=");
			requestData.append(config.getAccessKey());
			requestData.append("&");
			requestData.append("requestId=");
			requestData.append(requestId);
			requestData.append("&");
			requestData.append("amount=");
			requestData.append(String.valueOf(param.getPaymentFee().longValue()));
			requestData.append("&");
			requestData.append("orderId=");
			requestData.append(payAction.getMciAddId());
			requestData.append("&");
			requestData.append("orderInfo=");
			requestData.append("BV_Insurance");
			requestData.append("&");
			requestData.append("returnUrl=");
			requestData.append(config.getReturnUrl());
			requestData.append("&");
			requestData.append("notifyUrl=");
			requestData.append(config.getNotifyUrl());
			requestData.append("&");
			requestData.append("extraData=");
			requestData.append("");
			
			String signature = signSHA256(requestData.toString(), config.getSecretKey());
			
			MomoRequestDTO requestDTO = new MomoRequestDTO();
			requestDTO.setPartnerCode(config.getPartnerCode());
			requestDTO.setAccessKey(config.getAccessKey());
			requestDTO.setRequestId(requestId);
			requestDTO.setAmount(String.valueOf(param.getPaymentFee().longValue()));
			requestDTO.setOrderId(payAction.getMciAddId());
			requestDTO.setOrderInfo("BV_Insurance");
			requestDTO.setReturnUrl(config.getReturnUrl());
			requestDTO.setNotifyUrl(config.getNotifyUrl());
			requestDTO.setRequestType("captureMoMoWallet");
			requestDTO.setSignature(signature);
			
			return requestDTO;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	private String signSHA256(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.US_ASCII), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return Hex.encodeHexString(mac.doFinal(data.getBytes()));
	}

	@Override
	public PaymentResult processReturn(Map<String, String> paramMap, String vnpTmnCode) {
		String redirectUrl = applicationProperties.getPaymentReturnPage();
		PaymentResult result = new PaymentResult();
		if (validateSignature(paramMap)) {
			String orderId = paramMap.get(Constants.MOMO_PARAM_ORDER_ID);
			String transId = paramMap.get(Constants.MOMO_PARAM_TRANS_ID);
			Date now = new Date();
			if(!StringUtils.isEmpty(orderId)) {
				PayAction payAction = payActionRepository.findByMciAddId(orderId);
				if(payAction == null) {
					log.warn("ReturnMomo: Không tồn tại đơn hàng với mã :"+ orderId);
					result.setRedirectUrl(redirectUrl + "?paymentStatus=0");
					result.setResponseType(PaymentResponseType.ERROR);
				} else if(!paramMap.get(Constants.MOMO_PARAM_ERROR_CODE).equals(Constants.PAYMENT_MOMO_STATUS_SUCCESS)) {
					if(payAction.getPayEndDate() == null) {
						updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), transId, payAction);
						updateStatusSaleCode(payAction, now, transId, 90);
					}
					result.setRedirectUrl(redirectUrl + "?paymentStatus=0");
					result.setResponseType(PaymentResponseType.ERROR);
				} else {
					if(payAction.getPayEndDate() == null) {
						updatePaymentResult(PaymentStatus.SUCCESSFUL, payAction.getMciAddId(), transId, payAction);
						updateStatusSaleCode(payAction, now, transId, 91);
					}
					result.setRedirectUrl(redirectUrl + "?paymentStatus=3");
					result.setResponseType(PaymentResponseType.SUCCESS);
				}
			} else {
				log.warn("ReturnMomo: Không có mã đơn hàng trả về");
				result.setRedirectUrl(redirectUrl + "?paymentStatus=0");
				result.setResponseType(PaymentResponseType.ERROR);
			}
		} else {
			log.warn("ReturnMomo: Dữ liệu không khớp");
			result.setRedirectUrl(redirectUrl + "?paymentStatus=0");
			result.setResponseType(PaymentResponseType.ERROR);
		}
		return result;
	}
	
	private void updateStatusSaleCode(PayAction payAction, Date now, String transId, int status) {
		payAction.setPayEndDate(now);
		payAction.setTransactionId(transId);
		payAction.setStatus(status);
		payAction.setPayLog("\\n[Response]: ReturnMomo");
		payActionRepository.save(payAction);
	}
	
	private boolean validateSignature(Map<String, String> param) {
		try {
			StringBuffer requestData = new StringBuffer();
			for (Entry<String, String> entry : param.entrySet()) {
				if (!entry.getKey().equals(Constants.MOMO_PARAM_SIGNATURE)) {
					requestData.append(entry.getKey());
					requestData.append("=");
					requestData.append(entry.getValue());
					requestData.append("&");
				}
			}
			
			String data = requestData.substring(0, requestData.toString().length() - 1);
			String signature = signSHA256(data, config.getSecretKey());
			
			if(!signature.equalsIgnoreCase(param.get(Constants.MOMO_PARAM_SIGNATURE))) {
				return false;
			}
			
			return true;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return false;
		}
	}
}
