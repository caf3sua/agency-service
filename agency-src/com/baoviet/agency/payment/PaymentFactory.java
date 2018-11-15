package com.baoviet.agency.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.baoviet.agency.payment.common.PaymentType;
import com.baoviet.agency.payment.gateway.PaymentGateway;

@Component
public class PaymentFactory {

	@Autowired
	@Qualifier(value = "paymentGateway123Pay")
	private PaymentGateway paymentGateway123Pay;

	@Autowired
	@Qualifier(value = "paymentGatewayMomo")
	private PaymentGateway paymentGatewayMomo;

	@Autowired
	@Qualifier(value = "paymentGatewayViettelPay")
	private PaymentGateway paymentGatewayViettelPay;

	@Autowired
	@Qualifier(value = "paymentGatewayVnPay")
	private PaymentGateway paymentGatewayVnPay;
	
	@Autowired
	@Qualifier(value = "paymentGatewayCommon")
	private PaymentGateway paymentGatewayCommon;

	public PaymentGateway getPaymentGateway(PaymentType paymentType) {
		PaymentGateway paymentGateway = null;
		switch (paymentType) {
		case l23Pay:
			paymentGateway = paymentGateway123Pay;
			break;
		case Momo:
			paymentGateway = paymentGatewayMomo;
			break;
		case ViettelPay:
			paymentGateway = paymentGatewayViettelPay;
			break;
		case VnPay:
			paymentGateway = paymentGatewayVnPay;
			break;
		case Common:
			paymentGateway = paymentGatewayCommon;
			break;
		}
		return paymentGateway;
	}
}
