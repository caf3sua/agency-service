package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.dto.TviCareAddDTO;

/**
 * Service Interface for managing TviCareAdd.
 */
public interface TviCareAddService {

	String insertTviCareAdd(TviCareAddDTO info);
	
	TviCareAddDTO save(TviCareAddDTO tviCareAddDTO);
	
	List<TviCareAddDTO> getByTvicareId(String id);
}
