package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Tvicare;
import com.baoviet.agency.dto.TvicareDTO;
import com.baoviet.agency.repository.TvicareRepository;
import com.baoviet.agency.service.TvicareService;
import com.baoviet.agency.service.mapper.TvicareMapper;
import com.baoviet.agency.web.rest.ProductTvcResource;

/**
 * Service Implementation for managing TVI.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class TvicareServiceImpl implements TvicareService {

	private final Logger log = LoggerFactory.getLogger(ProductTvcResource.class);
	
	@Autowired
	private TvicareRepository tvicareRepository;

	@Autowired
	private TvicareMapper tviMapper;

	@Override
	public int insertTVI(TvicareDTO info) {
		log.debug("REST request to insertTVI : TvicareDTO{}", info);
		if (info != null) {
			try {
				Tvicare entity = tvicareRepository.save(tviMapper.toEntity(info));
				return Integer.parseInt(entity.getTvicareId());

			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public TvicareDTO save(TvicareDTO tvicareDTO) {
		log.debug("REST request to save : TvicareDTO{}", tvicareDTO);
		Tvicare entity = tviMapper.toEntity(tvicareDTO);
		Tvicare result = tvicareRepository.save(entity);
		return tviMapper.toDto(result);
	}

	@Override
	public TvicareDTO getById(String id) {
		log.debug("REST request to getById : id{}", id);
		Tvicare result = tvicareRepository.findOne(id);
		return tviMapper.toDto(result);
	}

}
