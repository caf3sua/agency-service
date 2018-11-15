package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Home;
import com.baoviet.agency.dto.HomeDTO;
import com.baoviet.agency.repository.HomeRepository;
import com.baoviet.agency.service.HomeService;
import com.baoviet.agency.service.mapper.HomeMapper;
import com.baoviet.agency.web.rest.ProductHomeResource;

/**
 * Service Implementation for managing Home.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class HomeServiceImpl implements HomeService {

	private final Logger log = LoggerFactory.getLogger(ProductHomeResource.class);

	@Autowired
	private HomeRepository homeRepository;

	@Autowired
	private HomeMapper honeMapper;

	@Override
	public String insertHome(HomeDTO info) {
		log.debug("REST request to insertHome : {}", info);
		if (info != null) {
			try {
				Home entity = homeRepository.save(honeMapper.toEntity(info));
				return entity.getHomeId();

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public HomeDTO save(HomeDTO homeDTO) {
		log.debug("REST request to saveHome : {}", homeDTO);
		// Convert to Entity
		Home entity = honeMapper.toEntity(homeDTO);
		Home result = homeRepository.save(entity);
		return honeMapper.toDto(result);
	}

	@Override
	public HomeDTO getById(String id) {
		log.debug("REST request to getByIdHome : {}", id);
		Home result = homeRepository.findOne(id);
		return honeMapper.toDto(result);
	}

}
