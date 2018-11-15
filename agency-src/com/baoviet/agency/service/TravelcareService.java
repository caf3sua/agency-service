package com.baoviet.agency.service;

import com.baoviet.agency.dto.TravelcareDTO;

/**
 * Service Interface for managing travelcare.
 */
public interface TravelcareService {

	int insertTravelcare(TravelcareDTO info);
	
	TravelcareDTO save(TravelcareDTO travelcareDTO);
	
	TravelcareDTO getById(String id);
}
