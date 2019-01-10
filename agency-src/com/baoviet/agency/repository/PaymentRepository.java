package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.dto.GiftCodeDTO;
import com.baoviet.agency.payment.domain.CodeMan;
import com.baoviet.agency.payment.domain.PaymentBank;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface PaymentRepository {
	List<PaymentBank> getBanksByPaymentCode(String paymentCode);
	
	GiftCodeDTO getGiftCodeByCodeAndEmail(String couponCode, String email);
	
	CodeMan getCode(String companyId, String departmentId, String companyName, String departmentName, String processYear);
}