package com.baoviet.agency.service;

import com.baoviet.agency.dto.PaymentMsbDTO;
import com.baoviet.agency.web.rest.vm.NotifyPaymentVM;
import com.baoviet.agency.web.rest.vm.PaymentATCSVM;
import com.baoviet.agency.web.rest.vm.PaymentUpdateOTPVM;


/**
 * Service Interface for managing Contact.
 */
public interface PaymentService {
	
	PaymentMsbDTO updatePaymentATCS(PaymentATCSVM param);
	
	boolean updatePaymentOTP(PaymentUpdateOTPVM param);
	
	NotifyPaymentVM updateAgrement(NotifyPaymentVM param, String agentId);
}

