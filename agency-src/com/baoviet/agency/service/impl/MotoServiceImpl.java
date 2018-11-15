package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Moto;
import com.baoviet.agency.dto.MotoDTO;
import com.baoviet.agency.repository.MotoRepository;
import com.baoviet.agency.service.MotoService;
import com.baoviet.agency.service.mapper.MotoMapper;

/**
 * Service Implementation for managing Moto.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class MotoServiceImpl implements MotoService {

	private final Logger log = LoggerFactory.getLogger(MotoServiceImpl.class);

	@Autowired
	private MotoRepository motoRepository;
	@Autowired
	private MotoMapper motoMapper;

	@Override
	public Integer insertMoto(MotoDTO info) {
		log.debug("REST request to insertMoto : {}", info);
		if (info != null) {
			try {
				Moto entity = motoRepository.save(motoMapper.toEntity(info));
				return Integer.parseInt(entity.getId());

			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public MotoDTO save(MotoDTO motoDTO) {
		log.debug("REST request to save : {}", motoDTO);
		// Convert to Entity
		Moto entity = motoMapper.toEntity(motoDTO);
		Moto result = motoRepository.save(entity);
		return motoMapper.toDto(result);
	}

	@Override
	public MotoDTO getById(String id) {
		log.debug("REST request to getById : {}", id);
		Moto result = motoRepository.findOne(id);
		return motoMapper.toDto(result);
	}

}
