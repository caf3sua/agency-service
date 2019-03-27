package com.baoviet.agency.service;


import com.baoviet.agency.dto.MotoHondaDTO;

/**
 * Service Interface for managing MotoHonda.
 */
public interface HondaService {
	
	Integer insertHonda(MotoHondaDTO info);
	
	MotoHondaDTO save(MotoHondaDTO MotoHondaDTO);
	
	MotoHondaDTO getById(String id);
}

