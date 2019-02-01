package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.CarRate;
import com.baoviet.agency.repository.CarRateRepository;
import com.baoviet.agency.service.CarRateService;

/**
 * Service Implementation for managing TVI.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class CarRateServiceImpl implements CarRateService {

	private final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);
	
	@Autowired
	private CarRateRepository carRateRepository;
	
	@Override
	public CarRate getCarRateByParam(String mucdich, Integer socho, String type) {
		log.debug("Request to getCarRateByParam : mucdich {}, socho{}, type{} : ", mucdich, socho, type);
		List<CarRate> data = carRateRepository
				.findBySeatNumberFromLessThanEqualAndSeatNumberToGreaterThanEqualAndPurposeOfUsageIdAndType(socho,
						socho, mucdich, type);
		return data.get(0);
	}

	@Override
	public CarRate getCarRateByParam(String mucdich, Integer socho, String type, String agencyid) {
		log.debug("Request to getCarRateByParam : mucdich {}, socho{}, type{} , agencyid {} : ", mucdich, socho, type, agencyid);
		List<CarRate> data = carRateRepository
				.findBySeatNumberFromLessThanEqualAndSeatNumberToGreaterThanEqualAndPurposeOfUsageIdAndTypeAndAgencyId(socho,
						socho, mucdich, type, agencyid);
		return data.get(0);
	}

}
