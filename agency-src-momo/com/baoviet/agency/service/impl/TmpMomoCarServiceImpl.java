package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.TmpMomoCar;
import com.baoviet.agency.dto.TmpMomoCarDTO;
import com.baoviet.agency.repository.TmpMomoCarRepository;
import com.baoviet.agency.service.TmpMomoCarService;
import com.baoviet.agency.service.mapper.TmpMomoCarMapper;


/**
 * Service Implementation for managing GnocCr.
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class TmpMomoCarServiceImpl implements TmpMomoCarService {

    private final Logger log = LoggerFactory.getLogger(AgencyServiceImpl.class);

    @Autowired
    private TmpMomoCarRepository tmpMomoCarRepository;
    
    @Autowired
    private TmpMomoCarMapper tmpMomoCarMapper;
    
	@Override
	public TmpMomoCarDTO findByRequestId(String requestId) {
		log.debug("Request to findByRequestId requestId{} ", requestId);
		TmpMomoCar data = tmpMomoCarRepository.findByRequestId(requestId);
		return tmpMomoCarMapper.toDto(data);
	}

	@Override
	public TmpMomoCarDTO save(TmpMomoCarDTO obj) {
		log.debug("Request to save TmpMomoCarDTO{} ", obj);
		TmpMomoCar data = tmpMomoCarRepository.save(tmpMomoCarMapper.toEntity(obj));
		return tmpMomoCarMapper.toDto(data);
	}

	@Override
	public TmpMomoCarDTO findByGycbhNumber(String gycbhNumber) {
		log.debug("Request to save findByGycbhNumber{} ", gycbhNumber);
		TmpMomoCar data = tmpMomoCarRepository.findByGycbhNumber(gycbhNumber);
		return tmpMomoCarMapper.toDto(data);
	}



    /*
	 * -------------------------------------------------
	 * ---------------- Private method -----------------
	 * -------------------------------------------------
	 */
}
