package com.baoviet.agency.payment.gateway.impl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.baoviet.agency.config.GateWayViettelPayConfig;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.payment.common.Constants;
import com.baoviet.agency.payment.common.PaymentResponseType;
import com.baoviet.agency.payment.common.PaymentStatus;
import com.baoviet.agency.payment.common.PaymentType;
import com.baoviet.agency.payment.common.ViettelCheckOrderStatus;
import com.baoviet.agency.payment.dto.PaymentResult;
import com.baoviet.agency.payment.dto.ViettelCheckOrderInfoRequest;
import com.baoviet.agency.payment.dto.ViettelCheckOrderInfoResponse;
import com.baoviet.agency.payment.dto.ViettelResponseDTO;
import com.baoviet.agency.payment.dto.ViettelUpdateOrderStatusRequest;
import com.baoviet.agency.payment.dto.ViettelUpdateOrderStatusResponse;
import com.baoviet.agency.payment.gateway.AbstractPaymentGateway;
import com.baoviet.agency.web.rest.vm.PaymentProcessRequestVM;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentGatewayViettelPay extends AbstractPaymentGateway {
	private final Logger log = LoggerFactory.getLogger(PaymentGateway123Pay.class);

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	@Autowired
	private GateWayViettelPayConfig config;

	@Override
	public PaymentResult processPayment(AgencyDTO currentAgency, PaymentProcessRequestVM param,
			List<Agreement> agreements) {
		log.info("START processPayment viettelpay");
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
		StringBuffer redirectUrl = new StringBuffer(config.getServiceUrl());
		redirectUrl.append("?");
		try {
			redirectUrl.append("version=");
			redirectUrl.append(config.getVersion());
			redirectUrl.append("&");
			redirectUrl.append("command=");
			redirectUrl.append(config.getCommand());
			redirectUrl.append("&");
			redirectUrl.append("merchant_code=");
			redirectUrl.append(config.getMerchantCode());
			redirectUrl.append("&");
			redirectUrl.append("merchant_trans_id=");
			redirectUrl.append(payAction.getMciAddId());
			redirectUrl.append("&");
			redirectUrl.append("desc=");
			redirectUrl.append("PAYMENT:" + payAction.getMciAddId());
			redirectUrl.append("&");
			redirectUrl.append("trans_amount=");
			redirectUrl.append(param.getPaymentFee().longValue());
			redirectUrl.append("&");

			String secureHash = getSecureHash(payAction, param);

			redirectUrl.append("secure_hash=");
			redirectUrl.append(secureHash);
			redirectUrl.append("&");
			redirectUrl.append("return_url=");
			redirectUrl.append(config.getReturnUrl());
		} catch (Exception e) {
			log.info("UnsupportedEncodingException: " + e.getMessage());
			result.setResponseType(PaymentResponseType.ERROR);
			return result;
		}

		// Insert sale code
		insertSaleCode(payAction, redirectUrl.toString(), param.getBankCode(), PaymentType.ViettelPay);

		log.info("END processPayment viettelpay");
		result.setResponseType(PaymentResponseType.SUCCESS);
		result.setRedirectUrl(redirectUrl.toString());
		return result;
	}

	private String getSecureHash(PayAction payAction, PaymentProcessRequestVM param) {
		StringBuffer requestData = new StringBuffer();
		try {
			requestData.append(config.getAccessCode());
			requestData.append(config.getCommand());
			requestData.append(config.getMerchantCode());
			requestData.append(payAction.getMciAddId());
			requestData.append(config.getReturnUrl());
			requestData.append(param.getPaymentFee().longValue());
			requestData.append(config.getVersion());

			return calculateRFC2104HMAC(requestData.toString(), config.getHashKey());
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

	private String calculateRFC2104HMAC(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.US_ASCII), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
	}

	@Override
	public PaymentResult processReturn(Map<String, String> paramMap) {
		String redirectUrl = applicationProperties.getPaymentReturnPage();
		PaymentResult result = new PaymentResult();
		String data = config.getAccessCode() + paramMap.get(Constants.VIETTEL_PAY_PARAM_AMOUNT) + paramMap.get(Constants.VIETTEL_PAY_PARAM_COMMAND)
						+ paramMap.get(Constants.VIETTEL_PAY_PARAM_MERCHANT_CODE) + paramMap.get(Constants.VIETTEL_PAY_PARAM_MERCHANT_TRANS_ID)
						+ paramMap.get(Constants.VIETTEL_PAY_PARAM_RESPONSE_CODE) + paramMap.get(Constants.VIETTEL_PAY_PARAM_VERSION);
		try {
			String hashData = calculateRFC2104HMAC(data, config.getHashKey());
			
			if(!hashData.equals(paramMap.get(Constants.VIETTEL_PAY_PARAM_SECURE_HASH))) {
				result.setRedirectUrl(redirectUrl + "?paymentStatus=0");
				result.setResponseType(PaymentResponseType.ERROR);
				return result;
			}
			
			PayAction payAction = payActionRepository.findByMciAddId(paramMap.get(Constants.VIETTEL_PAY_PARAM_MERCHANT_TRANS_ID));
			if(payAction != null && payAction.getPayEndDate() == null) {
				// Post data
				data = config.getAccessCode() + Constants.TRANS_INQUIRY + config.getMerchantCode() + payAction.getMciAddId() + config.getVersion();
				hashData = calculateRFC2104HMAC(data, config.getHashKey());
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

				ViettelResponseDTO viettelResponseDTO = null;
				ObjectMapper mapper = new ObjectMapper();
				MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
				map.add("cmd", Constants.TRANS_INQUIRY);
				map.add("merchant_code", config.getMerchantCode());
				map.add("order_id", payAction.getMciAddId());
				map.add("version", config.getVersion());
				map.add("secure_hash", hashData);
				HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

				ResponseEntity<String> responseEntity = restTemplate.exchange(config.getPostUrl(), HttpMethod.POST, entity, String.class);
				viettelResponseDTO = mapper.readValue(responseEntity.getBody(), ViettelResponseDTO.class);
				
				if(viettelResponseDTO != null) {
					data = config.getAccessCode() + config.getMerchantCode() + viettelResponseDTO.getOrderId() + viettelResponseDTO.getStatus()
							+ viettelResponseDTO.getVersion();
					hashData = calculateRFC2104HMAC(data, config.getHashKey());
				
					if(!hashData.equals(viettelResponseDTO.getSecureHash())) {
						result.setRedirectUrl(redirectUrl + "?paymentStatus=0");
						result.setResponseType(PaymentResponseType.ERROR);
					} else if(viettelResponseDTO.getStatus().equals(Constants.PAYMENT_VIETTEL_STATUS_SUCCESS)) {
						updatePaymentResult(PaymentStatus.SUCCESSFUL, payAction.getMciAddId(), viettelResponseDTO.getVtTransactionId(), payAction);
						// Update PayActionStatus
						updatePayActionStatus(payAction, 91);
						result.setRedirectUrl(redirectUrl + "?paymentStatus=3");
						result.setResponseType(PaymentResponseType.SUCCESS);
					} else {
						updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), viettelResponseDTO.getVtTransactionId(), payAction);
						// Update PayActionStatus
						updatePayActionStatus(payAction, 90);
						result.setRedirectUrl(redirectUrl + "?paymentStatus=0");
						result.setResponseType(PaymentResponseType.ERROR);
					}
				}
			} else {
				result.setRedirectUrl(redirectUrl + "?paymentStatus=0");
				result.setResponseType(PaymentResponseType.ERROR);
			}
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			result.setRedirectUrl(redirectUrl + "?paymentStatus=0");
			result.setResponseType(PaymentResponseType.ERROR);
		}
		return result;
	}
	
	public ViettelCheckOrderInfoResponse checkOrderInfor(ViettelCheckOrderInfoRequest orderInfoRequest) {
		ViettelCheckOrderInfoResponse checkOrderInfoResponse = new ViettelCheckOrderInfoResponse();
		String data = config.getAccessCode() + orderInfoRequest.getOrderId() + orderInfoRequest.getMerchantCode() + orderInfoRequest.getTransAmount();
		
		try {
			String hashData = calculateRFC2104HMAC(data, config.getHashKey());
			
			if(!hashData.equals(orderInfoRequest.getCheckSum())) {
				checkOrderInfoResponse.setOrderId(orderInfoRequest.getOrderId());
				checkOrderInfoResponse.setResponseCode(ViettelCheckOrderStatus.FAILED_DATA_NOT_VALID.getValue());
				checkOrderInfoResponse.setTransAmount(checkOrderInfoResponse.getTransAmount());
				return checkOrderInfoResponse;
			}
			
			PayAction payAction = payActionRepository.findByMciAddId(orderInfoRequest.getOrderId());
			
			if(payAction == null) {
				checkOrderInfoResponse.setOrderId(orderInfoRequest.getOrderId());
				checkOrderInfoResponse.setResponseCode(ViettelCheckOrderStatus.FAILED_PAY_ACTION_NOT_EXISTED.getValue());
				checkOrderInfoResponse.setTransAmount(checkOrderInfoResponse.getTransAmount());
				return checkOrderInfoResponse;
			} else {
				checkOrderInfoResponse.setOrderId(orderInfoRequest.getOrderId());
				checkOrderInfoResponse.setResponseCode(ViettelCheckOrderStatus.SUCCESSFUL.getValue());
				checkOrderInfoResponse.setTransAmount(checkOrderInfoResponse.getTransAmount());
				return checkOrderInfoResponse;
			}
		} catch (Exception e) {
			checkOrderInfoResponse.setOrderId(orderInfoRequest.getOrderId());
			checkOrderInfoResponse.setResponseCode(ViettelCheckOrderStatus.FAILED_DATA_NOT_VALID.getValue());
			checkOrderInfoResponse.setTransAmount(checkOrderInfoResponse.getTransAmount());
			return checkOrderInfoResponse;
		}
	}
	
	public ViettelUpdateOrderStatusResponse updateOrderStatus(ViettelUpdateOrderStatusRequest orderStatusRequest) {
		ViettelUpdateOrderStatusResponse orderStatusResponse = new ViettelUpdateOrderStatusResponse();
		String data = config.getAccessCode() + orderStatusRequest.getOrderId() + orderStatusRequest.getMerchantCode() 
						+ orderStatusRequest.getStatus() + orderStatusRequest.getTransAmount() + orderStatusRequest.getVtTransactionId();
		try {
			String hashData = calculateRFC2104HMAC(data, config.getHashKey());
			
			if(!hashData.equals(orderStatusRequest.getCheckSum())) {
				orderStatusResponse.setOrderId(orderStatusRequest.getOrderId());
				orderStatusResponse.setResponseCode(ViettelCheckOrderStatus.FAILED_DATA_NOT_VALID.getValue());
				orderStatusResponse.setTransAmount(orderStatusRequest.getTransAmount());
				return orderStatusResponse;
			}
			
			PayAction payAction = payActionRepository.findByMciAddId(orderStatusRequest.getOrderId());
			if(payAction != null && payAction.getPayEndDate() == null) {
				// Post data
				data = config.getAccessCode() + Constants.TRANS_INQUIRY + config.getMerchantCode() + payAction.getMciAddId() + config.getVersion();
				hashData = calculateRFC2104HMAC(data, config.getHashKey());
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

				ViettelResponseDTO viettelResponseDTO = null;
				ObjectMapper mapper = new ObjectMapper();
				MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
				map.add("cmd", Constants.TRANS_INQUIRY);
				map.add("merchant_code", config.getMerchantCode());
				map.add("order_id", payAction.getMciAddId());
				map.add("version", config.getVersion());
				map.add("secure_hash", hashData);
				HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

				ResponseEntity<String> responseEntity = restTemplate.exchange(config.getPostUrl(), HttpMethod.POST, entity, String.class);
				viettelResponseDTO = mapper.readValue(responseEntity.getBody(), ViettelResponseDTO.class);
				
				if(viettelResponseDTO != null) {
					data = config.getAccessCode() + config.getMerchantCode() + viettelResponseDTO.getOrderId() + viettelResponseDTO.getStatus()
							+ viettelResponseDTO.getVersion();
					hashData = calculateRFC2104HMAC(data, config.getHashKey());
				
					if(!hashData.equals(viettelResponseDTO.getSecureHash())) {
						orderStatusResponse.setOrderId(orderStatusRequest.getOrderId());
						orderStatusResponse.setResponseCode(ViettelCheckOrderStatus.FAILED_DATA_NOT_VALID.getValue());
						orderStatusResponse.setTransAmount(orderStatusRequest.getTransAmount());
					} else if(viettelResponseDTO.getStatus().equals(Constants.PAYMENT_VIETTEL_STATUS_SUCCESS)) {
						updatePaymentResult(PaymentStatus.SUCCESSFUL, payAction.getMciAddId(), viettelResponseDTO.getVtTransactionId(), payAction);
						updatePayActionStatus(payAction, 91);
						orderStatusResponse.setOrderId(orderStatusRequest.getOrderId());
						orderStatusResponse.setResponseCode(ViettelCheckOrderStatus.SUCCESSFUL.getValue());
						orderStatusResponse.setTransAmount(orderStatusRequest.getTransAmount());
					} else {
						updatePaymentResult(PaymentStatus.FAILED, payAction.getMciAddId(), viettelResponseDTO.getVtTransactionId(), payAction);
						updatePayActionStatus(payAction, 90);
						orderStatusResponse.setOrderId(orderStatusRequest.getOrderId());
						orderStatusResponse.setResponseCode(ViettelCheckOrderStatus.FAILED_PAY_ACTION_NOT_EXISTED.getValue());
						orderStatusResponse.setTransAmount(orderStatusRequest.getTransAmount());
					}
				}
				return orderStatusResponse;
			} else {
				orderStatusResponse.setOrderId(orderStatusRequest.getOrderId());
				orderStatusResponse.setResponseCode(ViettelCheckOrderStatus.FAILED_PAY_ACTION_NOT_EXISTED.getValue());
				orderStatusResponse.setTransAmount(orderStatusRequest.getTransAmount());
				return orderStatusResponse;
			}
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			orderStatusResponse.setOrderId(orderStatusRequest.getOrderId());
			orderStatusResponse.setResponseCode(ViettelCheckOrderStatus.FAILED_PAY_ACTION_NOT_EXISTED.getValue());
			orderStatusResponse.setTransAmount(orderStatusRequest.getTransAmount());
			return orderStatusResponse;
		}
	}
	
	private void updatePayActionStatus(PayAction payAction, int status) {
		payAction.setBankcode("VIETTELPAY");
		payAction.setStatus(status);
		payActionRepository.save(payAction);
	}
}
