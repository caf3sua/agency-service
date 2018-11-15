package com.baoviet.agency.service;

import java.util.List;
import java.util.Set;

import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.dto.AgencyDTO;

/**
 * Service Interface for managing Agency.
 */
public interface AgencyService {
	AgencyDTO save(AgencyDTO agencyDTO);
	
	AgencyDTO findByEmail(String email);
	
	boolean changePassword(String userLogin, String password);
	
	List<Agency> getAgencyByParent(String parent);
	
	Set<String> authoritiesToStrings(String email);
}

