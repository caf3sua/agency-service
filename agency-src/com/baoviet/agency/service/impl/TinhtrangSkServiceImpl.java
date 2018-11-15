package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.TinhtrangSk;
import com.baoviet.agency.dto.TinhtrangSkDTO;
import com.baoviet.agency.repository.TinhtrangSkRepository;
import com.baoviet.agency.service.TinhtrangSkService;
import com.baoviet.agency.service.mapper.TinhtrangSkMapper;


/**
 * Service Implementation for managing Agreement.
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class TinhtrangSkServiceImpl implements TinhtrangSkService {

    private final Logger log = LoggerFactory.getLogger(TinhtrangSkServiceImpl.class);

    @Autowired
    private TinhtrangSkMapper tinhtrangSkMapper;

    @Autowired
    private TinhtrangSkRepository tinhtrangSkRepository;
    
	@Override
	public TinhtrangSkDTO save(TinhtrangSkDTO dto) {
		log.debug("Request to save kcare, {}", dto);
		// Convert to Entity
		TinhtrangSk entity = tinhtrangSkMapper.toEntity(dto);
		TinhtrangSk result = tinhtrangSkRepository.save(entity);
		return tinhtrangSkMapper.toDto(result);
	}

	@Override
	public TinhtrangSkDTO findById(String tinhtrangSkId) {
		log.debug("Request to find kcare, {}", tinhtrangSkId);
		return tinhtrangSkMapper.toDto(tinhtrangSkRepository.findOne(tinhtrangSkId));
	}

	@Override
	public List<TinhtrangSkDTO> findByIdThamchieu(String id, String maSp) {
		log.debug("Request to  findByIdThamchieu, {}", id);
		List<TinhtrangSk> result = tinhtrangSkRepository.findByIdThamchieuAndMasanpham(id, maSp);
		return tinhtrangSkMapper.toDto(result);
	}

}
