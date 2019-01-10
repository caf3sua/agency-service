package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.PayAction;

/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface PayActionRepositoryExtend {

	List<PayAction> search(String fromDate, String toDate, String type);
	
	List<PayAction> searchAdmin(String fromDate, String toDate, String adminId);
	
	List<PayAction> search(String contactId, String type);
}