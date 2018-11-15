package com.baoviet.agency.service;


import com.baoviet.agency.dto.MotoDTO;

/**
 * Service Interface for managing Moto.
 */
public interface MotoService {
	
	Integer insertMoto(MotoDTO info);
	
	MotoDTO save(MotoDTO motoDTO);
	
	MotoDTO getById(String id);
}

