package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Pa;
import com.baoviet.agency.dto.PaDTO;
import com.baoviet.agency.repository.PaRepository;
import com.baoviet.agency.service.PaService;
import com.baoviet.agency.service.mapper.PaMapper;

/**
 * Service Implementation for managing TVI.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class PaServiceImpl implements PaService {

	private final Logger log = LoggerFactory.getLogger(ProductTncServiceImpl.class);

	@Autowired
	private PaRepository paRepository;
	@Autowired
	private PaMapper paMapper;

	@Override
	public String Insert(PaDTO info) {
		try {
			log.debug("Result of Insert, PaDTO {}", info);
			Pa entity = paRepository.save(paMapper.toEntity(info));
			log.debug("Result of Insert Result, PaId {}", entity.getPaId());
			return entity.getPaId();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public PaDTO getById(String id) {
		log.debug("Result of getById - PaDTO, {}", id);
		Pa entity = paRepository.findOne(id);
		return paMapper.toDto(entity);
	}

}
