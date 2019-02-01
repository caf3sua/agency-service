package com.baoviet.agency.service;


import com.baoviet.agency.dto.UploadDTO;

/**
 * Service Interface for managing TVC.
 */
public interface UploadService {
	
	String insertUpload(UploadDTO uploadDTO);
	
	UploadDTO save(UploadDTO uploadDTO);
}

