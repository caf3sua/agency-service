package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.MotoHonda;
import com.baoviet.agency.dto.MotoHondaDTO;
import com.baoviet.agency.repository.MotoHondaRepository;
import com.baoviet.agency.service.HondaService;
import com.baoviet.agency.service.mapper.HondaMapper;

/**
 * Service Implementation for managing Moto.
 * 
 * @author DucLM
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class HondaServiceImpl implements HondaService {

	private final Logger log = LoggerFactory.getLogger(HondaServiceImpl.class);

	@Autowired
	private MotoHondaRepository motoRepository;
	
	@Autowired
	private HondaMapper motoMapper;

	@Override
	public Integer insertHonda(MotoHondaDTO info) {
		log.debug("REST request to insertMoto : {}", info);
		if (info != null) {
			try {
				MotoHonda entity = motoRepository.save(motoMapper.toEntity(info));
				return Integer.parseInt(entity.getId());

			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public MotoHondaDTO save(MotoHondaDTO MotoHondaDTO) {
		log.debug("REST request to save : {}", MotoHondaDTO);
		// Convert to Entity
		MotoHonda entity = motoMapper.toEntity(MotoHondaDTO);
		MotoHonda result = motoRepository.save(entity);
		return motoMapper.toDto(result);
	}

	@Override
	public MotoHondaDTO getById(String id) {
		log.debug("REST request to getById : {}", id);
		MotoHonda result = motoRepository.findOne(id);
		return motoMapper.toDto(result);
	}

}
