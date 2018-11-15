package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Bvp;
import com.baoviet.agency.dto.BvpDTO;
import com.baoviet.agency.repository.BVPRepository;
import com.baoviet.agency.service.BVPService;
import com.baoviet.agency.service.mapper.BVPMapper;


/**
 * Service Implementation for managing TVI.
 * @author CuongTT
 */
@Service
@CacheConfig(cacheNames = "product")
@Transactional
public class BVPServiceImpl implements BVPService {
	
	private final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);
	
	@Autowired
	private BVPRepository bVPRepository;
	@Autowired
	private BVPMapper bVPMapper;

	@Override
	public String Insert(BvpDTO bvpDTO) {
		log.debug("Request to save BvpDTO : AttachmentDTO {} ", bvpDTO);
		try {
			Bvp entity = bVPRepository.save(bVPMapper.toEntity(bvpDTO));
			return entity.getId();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public BvpDTO getById(String id) {
		log.debug("Request to getById BvpDTO : id {} ", id);
		Bvp entity = bVPRepository.findOne(id);
		return bVPMapper.toDto(entity);
	}

	
}
