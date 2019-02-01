package com.baoviet.agency.service;


import com.baoviet.agency.dto.FilesDTO;

/**
 * Service Interface for managing TVC.
 */
public interface FilesService {
	
	String save(FilesDTO fileInfo);
	
	public FilesDTO findById(String id);
}

