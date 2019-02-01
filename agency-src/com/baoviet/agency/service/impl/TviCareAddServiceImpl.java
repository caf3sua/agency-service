package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.TviCareAdd;
import com.baoviet.agency.dto.TviCareAddDTO;
import com.baoviet.agency.repository.TviCareAddRepository;
import com.baoviet.agency.service.TviCareAddService;
import com.baoviet.agency.service.mapper.TviCareAddMapper;
import com.baoviet.agency.web.rest.ProductTvcResource;

/**
 * Service Implementation for managing TviCareAdd.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class TviCareAddServiceImpl implements TviCareAddService {

	private final Logger log = LoggerFactory.getLogger(ProductTvcResource.class);
	
	@Autowired
	private TviCareAddRepository tviCareAddRepository;

	@Autowired
	private TviCareAddMapper tviCareAddMapper;

	@Override
	public String insertTviCareAdd(TviCareAddDTO info) {
		log.debug("REST request to insertTviCareAdd : TviCareAddDTO{}", info);
		if (info != null) {
			try {
				TviCareAdd entity = tviCareAddRepository.save(tviCareAddMapper.toEntity(info));
				return entity.getTviAddId();

			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public TviCareAddDTO save(TviCareAddDTO tviCareAddDTO) {
		log.debug("REST request to save : TviCareAddDTO{}", tviCareAddDTO);
		TviCareAdd entity = tviCareAddMapper.toEntity(tviCareAddDTO);
		TviCareAdd result = tviCareAddRepository.save(entity);
		return tviCareAddMapper.toDto(result);
	}

	@Override
	public List<TviCareAddDTO> getByTvicareId(String id) {
		log.debug("REST request to getByTvicareId : id{}", id);
		List<TviCareAdd> result = tviCareAddRepository.findByTravaelcareId(id);
		return tviCareAddMapper.toDto(result);
	}
}