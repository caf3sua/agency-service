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

import com.baoviet.agency.domain.AgentDocument;
import com.baoviet.agency.dto.AgentDocumentDTO;
import com.baoviet.agency.repository.AgentDocumentRepository;
import com.baoviet.agency.service.AgentDocumentService;
import com.baoviet.agency.service.mapper.AgentDocumentMapper;

import sun.misc.BASE64Decoder;


/**
 * Service Implementation for managing AgentDocument.
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class AgentDocumentServiceImpl implements AgentDocumentService {
	
	private final Logger log = LoggerFactory.getLogger(AgentDocumentServiceImpl.class);
	
	@Autowired
	private AgentDocumentRepository agentDocumentRepository;
	
	@Autowired
	private AgentDocumentMapper agentDocumentMapper;
	
	@Override
	public List<AgentDocumentDTO> getAll() {
		log.debug("Request to get all document");
		
		List<AgentDocumentDTO> lstAgentDocument = agentDocumentMapper.toDto(agentDocumentRepository.findAll());
		return lstAgentDocument;
	}

	@Override
	public AgentDocumentDTO save(AgentDocumentDTO agentDocumentDTO) {
		log.debug("Request to save document : {} ", agentDocumentDTO);
		
    	try {
    		AgentDocument entity = agentDocumentMapper.toEntity(agentDocumentDTO);
    		
    		// Convert to byte[]
    		BASE64Decoder decoder = new BASE64Decoder();
			byte[] imageByte = decoder.decodeBuffer(agentDocumentDTO.getContentFile());
			
			entity.setContent(imageByte);
			// upload date
			if (agentDocumentDTO.getDateUpload() == null) {
				entity.setDateUpload(new Date());
			}
			
			
			entity = agentDocumentRepository.save(entity);
			return agentDocumentMapper.toDto(entity);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public AgentDocumentDTO findById(String id) {
		log.debug("Request to find by Id : {} ", id);
		return agentDocumentMapper.toDto(agentDocumentRepository.findOne(id));
	}
	
	
}
