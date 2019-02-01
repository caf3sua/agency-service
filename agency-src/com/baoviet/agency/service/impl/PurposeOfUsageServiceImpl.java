package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.PurposeOfUsage;
import com.baoviet.agency.dto.PurposeOfUsageDTO;
import com.baoviet.agency.repository.PurposeOfUsageRepository;
import com.baoviet.agency.service.PurposeOfUsageService;
import com.baoviet.agency.service.mapper.PurposeOfUsageMapper;

/**
 * Service Implementation for managing TVI.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class PurposeOfUsageServiceImpl implements PurposeOfUsageService {
	
	private final Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);
	
	@Autowired
	private PurposeOfUsageRepository purposeOfUsageRepository;

	@Autowired
	private PurposeOfUsageMapper purposeOfUsageMapper;

	@Override
	public PurposeOfUsageDTO getPurposeOfUsageById(String purposeOfUsageId) {
		log.debug("REST request to getPurposeOfUsageById, {} :", purposeOfUsageId);
		PurposeOfUsage entity = purposeOfUsageRepository.findOne(purposeOfUsageId);
		PurposeOfUsageDTO dto = purposeOfUsageMapper.toDto(entity);
		return dto;
	}

	@Override
	public List<PurposeOfUsageDTO> GetbyCategory(String category_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurposeOfUsageDTO> getallpurposeOfUsagesbydataset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updatepurposeOfUsage(PurposeOfUsageDTO purposeOfUsage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insertpurposeOfUsage(PurposeOfUsageDTO purposeOfUsage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deletepurposeOfUsage(String PURPOSE_OF_USAGE_ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurposeOfUsageDTO> getallpurposeOfUsages() {
		// TODO Auto-generated method stub
		return null;
	}

}
