package com.baoviet.agency.payment.gateway.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.payment.domain.PaymentBank;
import com.baoviet.agency.payment.dto.PaymentResult;
import com.baoviet.agency.payment.gateway.AbstractPaymentGateway;
import com.baoviet.agency.web.rest.vm.PaymentProcessRequestVM;

@Service
public class PaymentGatewayCommon extends AbstractPaymentGateway {
	private final Logger log = LoggerFactory.getLogger(PaymentGatewayCommon.class);

	@Override
	public PaymentResult processPayment(AgencyDTO currentAgency, PaymentProcessRequestVM param,
			List<Agreement> agreements) {
		return null;
	}

	@Override
	public List<PaymentBank> getBanksByPaymentCode(String paymentCode) {
		return super.getBanksByPaymentCode(paymentCode);
	}

	@Override
	public PaymentResult processReturn(Map<String, String> paramMap, String vnpTmnCode) {
		return null;
	}
}
