package com.baoviet.agency.service;


import java.util.List;

import com.baoviet.agency.dto.TlAddDTO;

/**
 * Service Interface for managing TlAddDTO.
 */
public interface TlAddService {
	
	String insertTlAdd(TlAddDTO info);
	
	List<TlAddDTO> getAllByTlId(String id);
	
}

