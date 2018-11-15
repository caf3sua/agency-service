package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.TravelCareAdd;
import com.baoviet.agency.dto.TravelCareAddDTO;
import com.baoviet.agency.repository.TravelCareAddRepository;
import com.baoviet.agency.service.TravelCareAddService;
import com.baoviet.agency.service.mapper.TravelCareAddMapper;

/**
 * Service Implementation for managing Travelcare.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class TravelCareAddServiceImpl implements TravelCareAddService {

	private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	private TravelCareAddRepository travelCareAddRepository;

	@Autowired
	private TravelCareAddMapper travelCareAddMapper;

	@Override
	public int insertTravelCareAdd(TravelCareAddDTO info) {
		log.debug("REST request to insertTravelCareAdd, TravelCareAddDTO{} :", info);
		if (info != null) {
			try {
				TravelCareAdd entity = travelCareAddRepository.save(travelCareAddMapper.toEntity(info));
				return Integer.parseInt(entity.getTvcAddId());

			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public List<TravelCareAddDTO> getByTravaelcareId(String id) {
		log.debug("REST request to getByTravaelcareId, id{} :", id);
		List<TravelCareAdd> result = travelCareAddRepository.findByTravaelcareId(id);
		return travelCareAddMapper.toDto(result);
	}

}
