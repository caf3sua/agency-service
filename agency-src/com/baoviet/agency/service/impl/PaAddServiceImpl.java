package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.PaAdd;
import com.baoviet.agency.dto.PaAddDTO;
import com.baoviet.agency.repository.PaAddRepository;
import com.baoviet.agency.service.PaAddService;
import com.baoviet.agency.service.mapper.PaAddMapper;

/**
 * Service Implementation for managing TVI.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class PaAddServiceImpl implements PaAddService {

	private final Logger log = LoggerFactory.getLogger(MotoServiceImpl.class);
	
	@Autowired
	private PaAddRepository paRepository;
	@Autowired
	private PaAddMapper paMapper;

	@Override
	public String Insert(PaAddDTO info) {
		log.debug("REST request to Insert - PaAddDTO : {}", info);
		try {
			PaAdd entity = paRepository.save(paMapper.toEntity(info));
			return entity.getPaAddId();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void Delete(String id) {
		log.debug("REST request to Delete - PaAddDTO : {}", id);
		try {
			paRepository.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public List<PaAddDTO> getByPaId(String id) {
		log.debug("REST request to getByPaId - PaAddDTO : {}", id);
		List<PaAdd> entity = paRepository.findByPaId(id);
		return paMapper.toDto(entity);
	}

}
