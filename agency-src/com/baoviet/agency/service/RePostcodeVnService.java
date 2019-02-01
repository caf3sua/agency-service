package com.baoviet.agency.service;


import java.util.List;

import com.baoviet.agency.dto.RePostcodeVnDTO;

/**
 * Service Interface for managing TVC.
 */
public interface RePostcodeVnService {
	
	List<RePostcodeVnDTO> getAll();
	
	RePostcodeVnDTO getAddressByCode(String code);
}

