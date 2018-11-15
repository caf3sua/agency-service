package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.ContactRelationship;
import com.baoviet.agency.dto.ContactRelationshipDTO;
import com.baoviet.agency.repository.AgentContactRelationshipRepository;
import com.baoviet.agency.service.AgentContactRelationshipService;
import com.baoviet.agency.service.mapper.AgentContactRelationshipMapper;


/**
 * Service Implementation for managing ContactRelationship.
 * @author DucLM
 */
@Service
@CacheConfig(cacheNames = "product")
@Transactional
public class AgentContactRelationshipServiceImpl implements AgentContactRelationshipService {
	
	private final Logger log = LoggerFactory.getLogger(AgencyServiceImpl.class);
	
	@Autowired
	private AgentContactRelationshipRepository contactRelationshipRepository;
	
	@Autowired
	private AgentContactRelationshipMapper contactRelationshipMapper;

	@Override
	public ContactRelationshipDTO save(ContactRelationshipDTO info) {
		log.debug("Request to save ContactRelationshipDTO, {}", info);
		try {
			ContactRelationship entity = contactRelationshipRepository.save(contactRelationshipMapper.toEntity(info));
			return contactRelationshipMapper.toDto(entity);
		} catch (Exception e) {
			return null;
		}
	}

	
}
