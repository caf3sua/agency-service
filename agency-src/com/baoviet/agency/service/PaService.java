package com.baoviet.agency.service;


import com.baoviet.agency.dto.PaDTO;

/**
 * Service Interface for managing TVC.
 */
public interface PaService {
	
	String Insert(PaDTO info);
	
	PaDTO getById(String id);
}

