package com.baoviet.agency.service;

import com.baoviet.agency.dto.TvicareDTO;

/**
 * Service Interface for managing TVI.
 */
public interface TvicareService {

	int insertTVI(TvicareDTO info);
	
	TvicareDTO save(TvicareDTO tvicareDTO);
	
	TvicareDTO getById(String id);
}
