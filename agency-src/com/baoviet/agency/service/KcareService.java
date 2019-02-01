package com.baoviet.agency.service;

import com.baoviet.agency.dto.KcareDTO;

/**
 * Service Interface for managing Contact.
 */
public interface KcareService {
	
	KcareDTO save(KcareDTO dto);
	
	KcareDTO findById(String kcareId);
	
}

