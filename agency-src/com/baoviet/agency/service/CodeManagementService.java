package com.baoviet.agency.service;

import com.baoviet.agency.dto.CodeManagementDTO;
import com.baoviet.agency.exception.AgencyBusinessException;

/**
 * Service Interface for managing CodeManagementService.
 */
public interface CodeManagementService {
	/**
	 * Get code management
	 * @param year last 2 char of current year
	 * @param type 
	 * @param nv product code (CAR, KCARE...)
	 * @return
	 */
	String getCode(String year, String type, String nv);
	
	String getIssueNumber(String type, String nv);
	
	CodeManagementDTO getById(String id);
	
	CodeManagementDTO getCodeManagement(String productName) throws AgencyBusinessException;
}

