package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.dto.TinhtrangSkDTO;

/**
 * Service Interface for managing TinhtrangSk.
 */
public interface TinhtrangSkService {
	
	TinhtrangSkDTO save(TinhtrangSkDTO dto);
	
	TinhtrangSkDTO findById(String tinhtrangSkId);
	
	List<TinhtrangSkDTO> findByIdThamchieu(String id, String maSp);
}

