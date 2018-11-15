package com.baoviet.agency.service;


import com.baoviet.agency.dto.HomeDTO;

/**
 * Service Interface for managing TVC.
 */
public interface HomeService {
	
	String insertHome(HomeDTO info);
	
	HomeDTO save(HomeDTO homeDTO);
	
	HomeDTO getById(String id);
}

