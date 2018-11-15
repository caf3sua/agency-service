package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.AgencyRelation;
import com.baoviet.agency.repository.AgencyRelationRepository;
import com.baoviet.agency.service.AgencyRelationService;

/**
 * Service Implementation for managing GnocCr.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class AgencyRelationServiceImpl implements AgencyRelationService {

	private final Logger log = LoggerFactory.getLogger(AgencyRelationServiceImpl.class);

	@Autowired
	private AgencyRelationRepository agencyRelationRepository;

	@Override
	public List<AgencyRelation> getListChiNhanhOrPGD(String parent, String type) {
		log.debug("Request to getListChiNhanhOrPGD, {}", parent);
		List<AgencyRelation> data = agencyRelationRepository.findByParrenetIdAndType(parent, type);
		return data;
	}

	@Override
	public AgencyRelation getById(String id) {
		log.debug("Request to getById, {}", id);
		AgencyRelation data = agencyRelationRepository.findOne(id);
		if (data != null) {
			return data;	
		}
		return null;
	}

}
