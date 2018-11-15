package com.baoviet.agency.service;


import java.util.List;

import com.baoviet.agency.domain.Attachment;
import com.baoviet.agency.dto.AttachmentDTO;

/**
 * Service Interface for managing AttachmentDTO.
 */
public interface AttachmentService {
	
	String save(AttachmentDTO param);
	
	List<Attachment> getByParrentId(String conversationId);
}

