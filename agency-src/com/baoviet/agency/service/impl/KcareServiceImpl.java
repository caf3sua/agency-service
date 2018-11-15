package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Kcare;
import com.baoviet.agency.dto.KcareDTO;
import com.baoviet.agency.repository.KcareRepository;
import com.baoviet.agency.service.KcareService;
import com.baoviet.agency.service.mapper.KcareMapper;

/**
 * Service Implementation for managing Agreement.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class KcareServiceImpl implements KcareService {

	private final Logger log = LoggerFactory.getLogger(KcareServiceImpl.class);

	@Autowired
	private KcareMapper kcareMapper;

	@Autowired
	private KcareRepository kcareRepository;

	@Override
	public KcareDTO save(KcareDTO dto) {
		log.debug("Request to save kcare, {}", dto);
		// Convert to Entity
		Kcare entity = kcareMapper.toEntity(dto);
		Kcare result = kcareRepository.save(entity);
		return kcareMapper.toDto(result);
	}

	@Override
	public KcareDTO findById(String kcareId) {
		log.debug("Request to find kcare, {}", kcareId);
		return kcareMapper.toDto(kcareRepository.findOne(kcareId));
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
}
