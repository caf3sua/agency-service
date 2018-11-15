package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.TvcPlaneAdd;
import com.baoviet.agency.dto.TvcPlaneAddDTO;
import com.baoviet.agency.repository.TvcPlaneAddRepository;
import com.baoviet.agency.service.TvcPlaneAddService;
import com.baoviet.agency.service.mapper.TvcPlaneAddMapper;
import com.baoviet.agency.web.rest.ProductTvcResource;

/**
 * Service Implementation for managing TvcPlaneAdd.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class TvcPlaneAddServiceImpl implements TvcPlaneAddService {

	private final Logger log = LoggerFactory.getLogger(ProductTvcResource.class);
	
	@Autowired
	private TvcPlaneAddRepository tvcPlaneAddRepository;

	@Autowired
	private TvcPlaneAddMapper tvcPlaneAddMapper;

	@Override
	public String insert(TvcPlaneAddDTO info) {
		log.debug("REST request to insert : TvcPlaneAddDTO{}", info);
		if (info != null) {
			try {
				TvcPlaneAdd entity = tvcPlaneAddRepository.save(tvcPlaneAddMapper.toEntity(info));
				return entity.getTvcPlaneAddId();

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

}
