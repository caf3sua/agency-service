package com.baoviet.agency.service;


import com.baoviet.agency.dto.TlDTO;

/**
 * Service Interface for managing TVC.
 */
public interface TlService {
	
	String insertTl(TlDTO info);
	
	TlDTO save(TlDTO tlDTO);
	
	TlDTO getById(String id);
}

