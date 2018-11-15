package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Tl;
import com.baoviet.agency.dto.TlDTO;
import com.baoviet.agency.repository.TlRepository;
import com.baoviet.agency.service.TlService;
import com.baoviet.agency.service.mapper.TlMapper;
import com.baoviet.agency.web.rest.ProductKhcResource;

/**
 * Service Implementation for managing Tl.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class TlServiceImpl implements TlService {

	private final Logger log = LoggerFactory.getLogger(ProductKhcResource.class);
	
	@Autowired
	private TlRepository tlRepository;

	@Autowired
	private TlMapper tlMapper;

	@Override
	public String insertTl(TlDTO info) {
		log.debug("REST request to insertTl : {}", info);
		if (info != null) {
			try {
				Tl entity = tlRepository.save(tlMapper.toEntity(info));
				return entity.getTlId();
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public TlDTO save(TlDTO tlDTO) {
		log.debug("REST request to save : {}", tlDTO);
		// Convert to Entity
		Tl entity = tlMapper.toEntity(tlDTO);
		Tl result = tlRepository.save(entity);
		return tlMapper.toDto(result);
	}

	@Override
	public TlDTO getById(String id) {
		log.debug("REST request to getById : {}", id);
		Tl result = tlRepository.findOne(id);
		return tlMapper.toDto(result);
	}
}
