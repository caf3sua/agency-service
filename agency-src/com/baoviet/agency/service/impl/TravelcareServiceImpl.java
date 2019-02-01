package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Travelcare;
import com.baoviet.agency.dto.TravelcareDTO;
import com.baoviet.agency.repository.TravelcareRepository;
import com.baoviet.agency.service.TravelcareService;
import com.baoviet.agency.service.mapper.TravelcareMapper;
import com.baoviet.agency.web.rest.ProductTvcResource;

/**
 * Service Implementation for managing Travelcare.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class TravelcareServiceImpl implements TravelcareService {

	private final Logger log = LoggerFactory.getLogger(ProductTvcResource.class);

	@Autowired
	private TravelcareRepository travelcareRepository;

	@Autowired
	private TravelcareMapper travelcareMapper;

	@Override
	public int insertTravelcare(TravelcareDTO info) {
		log.debug("REST request to insertTravelcare : {}", info);
		if (info != null) {
			try {
				Travelcare entity = travelcareRepository.save(travelcareMapper.toEntity(info));
				return Integer.parseInt(entity.getTravelcareId());

			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public TravelcareDTO save(TravelcareDTO travelcareDTO) {
		log.debug("REST request to saveTravelcare : {}", travelcareDTO);
		// Convert to Entity
		Travelcare entity = travelcareMapper.toEntity(travelcareDTO);
		Travelcare result = travelcareRepository.save(entity);
		return travelcareMapper.toDto(result);
	}

	@Override
	public TravelcareDTO getById(String id) {
		log.debug("REST request to getByIdTravelcare : {}", id);
		Travelcare result = travelcareRepository.findOne(id);
		return travelcareMapper.toDto(result);
	}

}
