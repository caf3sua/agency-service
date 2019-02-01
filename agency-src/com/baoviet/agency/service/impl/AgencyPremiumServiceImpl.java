package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.KcareRate;
import com.baoviet.agency.repository.KcareRateRepository;
import com.baoviet.agency.service.AgencyPremiumService;


/**
 * Service Implementation for managing GnocCr.
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class AgencyPremiumServiceImpl implements AgencyPremiumService {

    private final Logger log = LoggerFactory.getLogger(AgencyPremiumServiceImpl.class);

    //private final GnocCrMapper gnocCrMapper;
    
    @Autowired
    private KcareRateRepository kcareRateRepository;

	@Override
	public KcareRate getAllKcareRateByParams(int age, String sex, String program) {
		log.debug("Request to getAllKcareRateByParams : age {},  sex {}, program {} ", age, sex, program);
		
		List<KcareRate> data = kcareRateRepository.findAllByAgeAndSexAndProgram(age, sex, program);
		
		if (data != null && data.size() > 0) {
			return data.get(0);
		}

		return null;
	}
    
	/*
	 * -------------------------------------------------
	 * ---------------- Private method -----------------
	 * -------------------------------------------------
	 */
}
