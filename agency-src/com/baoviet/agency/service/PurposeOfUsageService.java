package com.baoviet.agency.service;


import java.util.List;

import com.baoviet.agency.dto.PurposeOfUsageDTO;

/**
 * Service Interface for managing TVC.
 */
public interface PurposeOfUsageService {
	
	PurposeOfUsageDTO getPurposeOfUsageById(String purposeOfUsageId);
	
	List<PurposeOfUsageDTO> GetbyCategory(String category_id);
	
	List<PurposeOfUsageDTO> getallpurposeOfUsagesbydataset();
	
	public Boolean updatepurposeOfUsage(PurposeOfUsageDTO purposeOfUsage);
	
	public Integer insertpurposeOfUsage(PurposeOfUsageDTO purposeOfUsage);
	
	public Boolean deletepurposeOfUsage(String PURPOSE_OF_USAGE_ID);
	
	public List<PurposeOfUsageDTO> getallpurposeOfUsages();
}

