package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.TlAdd;
import com.baoviet.agency.dto.TlAddDTO;
import com.baoviet.agency.repository.TlAddRepository;
import com.baoviet.agency.service.TlAddService;
import com.baoviet.agency.service.mapper.TlAddMapper;

/**
 * Service Implementation for managing TlAdd.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class TlAddServiceImpl implements TlAddService {

	private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private TlAddRepository tlAddRepository;
	
	@Autowired
	private TlAddMapper tlAddMapper;

	@Override
	public String insertTlAdd(TlAddDTO info) {
		log.debug("REST request to insertTlAdd, TlAddDTO{} :", info);
		if (info != null) {
			try {
				TlAdd entity = tlAddRepository.save(tlAddMapper.toEntity(info));
				return entity.getTlAddId();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<TlAddDTO> getAllByTlId(String id) {
		log.debug("REST request to getAllByTlId, id{} :", id);
		List<TlAdd> entity = tlAddRepository.findByTlId(id);
		return tlAddMapper.toDto(entity);
	}
}
