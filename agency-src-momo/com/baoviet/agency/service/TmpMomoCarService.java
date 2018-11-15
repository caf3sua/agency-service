package com.baoviet.agency.service;

import com.baoviet.agency.dto.TmpMomoCarDTO;

/**
 * Service Interface for managing Agency.
 */
public interface TmpMomoCarService {

	TmpMomoCarDTO findByRequestId(String requestId);
	
	TmpMomoCarDTO save(TmpMomoCarDTO obj);
	
	TmpMomoCarDTO findByGycbhNumber(String gycbhNumber);
}

