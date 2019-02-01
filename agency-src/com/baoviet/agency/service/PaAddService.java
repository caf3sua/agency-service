package com.baoviet.agency.service;


import java.util.List;

import com.baoviet.agency.dto.PaAddDTO;

/**
 * Service Interface for managing TVC.
 */
public interface PaAddService {
	
	String Insert(PaAddDTO info);
	
	void Delete(String id);
	
	List<PaAddDTO> getByPaId(String id);
}

