package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.TvcPlane;
import com.baoviet.agency.dto.TvcPlaneDTO;
import com.baoviet.agency.repository.TvcPlaneRepository;
import com.baoviet.agency.service.TvcPlaneService;
import com.baoviet.agency.service.mapper.TvcPlaneMapper;
import com.baoviet.agency.web.rest.ProductTvcResource;

/**
 * Service Implementation for managing Home.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class TvcPlaneServiceImpl implements TvcPlaneService {

	private final Logger log = LoggerFactory.getLogger(ProductTvcResource.class);

	@Autowired
	private TvcPlaneRepository tvcPlaneRepository;

	@Autowired
	private TvcPlaneMapper tvcPlaneMapper;

	@Override
	public Integer insert(TvcPlaneDTO info) {
		log.debug("REST request to insert : TvcPlaneDTO{}", info);
		if (info != null) {
			try {
				TvcPlane entity = tvcPlaneRepository.save(tvcPlaneMapper.toEntity(info));
				return Integer.parseInt(entity.getTvcPlaneId());

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

}
