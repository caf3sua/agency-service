package com.baoviet.agency.service;


import java.util.List;

import com.baoviet.agency.dto.AgentDocumentDTO;

/**
 * Service Interface for managing AgentDocument.
 */
public interface AgentDocumentService {
	
	List<AgentDocumentDTO> getAll();
	
	AgentDocumentDTO save(AgentDocumentDTO agentDocumentDTO);
	
	AgentDocumentDTO findById(String id);
}

