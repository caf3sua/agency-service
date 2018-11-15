package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.PayActionDTO;


/**
 * Service Interface for managing Contact.
 */
public interface PayActionService {
	
	String save(PayAction info);
	
	List<PayActionDTO> search(String fromDate, String toDate, String agentId);
	
	List<PayActionDTO> searchAdmin(String fromDate, String toDate, String adminId);
	
	List<PayActionDTO> search(String contactId, String agentId);
}

