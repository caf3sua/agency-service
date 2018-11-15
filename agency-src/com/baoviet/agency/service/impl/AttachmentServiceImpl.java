package com.baoviet.agency.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Attachment;
import com.baoviet.agency.dto.AttachmentDTO;
import com.baoviet.agency.repository.AttachmentRepository;
import com.baoviet.agency.service.AttachmentService;
import com.baoviet.agency.service.mapper.AttachmentMapper;

import sun.misc.BASE64Decoder;


/**
 * Service Implementation for managing Attachment.
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class AttachmentServiceImpl implements AttachmentService {
	
	private final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Autowired
	private AttachmentMapper attachmentMapper;

	@Override
	public String save(AttachmentDTO param) {
		log.debug("Request to save files : AttachmentDTO {} ", param);
		
    	try {
    		Attachment entity = attachmentMapper.toEntity(param);
    		
    		// Convert to byte[]
    		BASE64Decoder decoder = new BASE64Decoder();
			byte[] imageByte = decoder.decodeBuffer(param.getContentFile());
			
			entity.setContent(imageByte);
			// upload date
			if (param.getCreateDate() == null) {
				entity.setCreateDate(new Date());
			}
			
			// length
			if (param.getContentLeng() == null || param.getContentLeng() == 0) {
				entity.setContentLeng((double)imageByte.length);
			}
			
			entity = attachmentRepository.save(entity);
			return entity.getAttachmentId();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<Attachment> getByParrentId(String conversationId) {
		return attachmentRepository.findByParrentId(conversationId);
	}
	
}
