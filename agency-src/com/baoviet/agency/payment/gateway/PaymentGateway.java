package com.baoviet.agency.payment.gateway;

import java.util.List;
import java.util.Map;

import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.payment.common.PaymentResponseType;
import com.baoviet.agency.payment.domain.PaymentBank;
import com.baoviet.agency.payment.dto.PaymentResult;
import com.baoviet.agency.web.rest.vm.PaymentProcessRequestVM;

public interface PaymentGateway {
	public PaymentResult processPayment(AgencyDTO currentAgency, PaymentProcessRequestVM param, List<Agreement> agreements); 
	
	public List<PaymentBank> getBanksByPaymentCode(String paymentCode); 
	
	public PaymentResult processReturn(Map<String, String> paramMap, String vnpTmnCode) throws AgencyBusinessException;
	
	public PaymentResponseType checkGiftCode(String giftCode, String email, List<Agreement> agreements);
}
