package com.baoviet.agency.payment.gateway.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.config.GateWay123PayConfig;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.payment.common.PaymentResponseType;
import com.baoviet.agency.payment.common.PaymentType;
import com.baoviet.agency.payment.dto.PaymentResult;
import com.baoviet.agency.payment.dto.PaymentResultVnPay;
import com.baoviet.agency.payment.gateway.AbstractPaymentGateway;
import com.baoviet.agency.web.rest.vm.PaymentProcessRequestVM;

@Service
public class PaymentGateway123Pay extends AbstractPaymentGateway {
	private final Logger log = LoggerFactory.getLogger(PaymentGateway123Pay.class);

	@Autowired
	private GateWay123PayConfig config;
	
	@Override
	public PaymentResult processPayment(AgencyDTO currentAgency, PaymentProcessRequestVM param, List<Agreement> agreements) {
		log.info("START processPayment PaymentGateway123Pay");
		PaymentResult result = new PaymentResult();
		
		// Create new pay action
		PayAction payAction = new PayAction();
		
		PaymentResponseType paymentResponseType = super.processPayment(config, payAction, currentAgency, param, agreements);
		
		log.info("paymentResponseType from parent: " + paymentResponseType.name());
		if(paymentResponseType != PaymentResponseType.SUCCESS) {
			result.setResponseType(paymentResponseType);
			return result;
		}
		
		// Build redirectUrl
		String redirectUrl = "";
		
		// Insert sale code
		insertSaleCode(payAction, redirectUrl, param.getBankCode(), PaymentType.l23Pay);
		
		log.info("END processPayment PaymentGateway123Pay");
		result.setResponseType(PaymentResponseType.SUCCESS);
		result.setRedirectUrl(redirectUrl);
		return result;
	}

	@Override
	public PaymentResult processReturn(Map<String, String> paramMap, String vnpTmnCode) {
		return null;
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
