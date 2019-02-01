package com.baoviet.agency.service;


import com.baoviet.agency.dto.BvpDTO;

/**
 * Service Interface for managing TVC.
 */
public interface BVPService {
	
	String Insert(BvpDTO bvpDTO);
	
	BvpDTO getById(String id);
}

