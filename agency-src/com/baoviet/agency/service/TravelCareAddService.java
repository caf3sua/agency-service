package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.dto.TravelCareAddDTO;

/**
 * Service Interface for managing TravelCareAdd.
 */
public interface TravelCareAddService {

	int insertTravelCareAdd(TravelCareAddDTO info);
	
	List<TravelCareAddDTO> getByTravaelcareId(String id);
}
