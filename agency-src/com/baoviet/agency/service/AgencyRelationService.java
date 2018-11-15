package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.domain.AgencyRelation;

/**
 * Service Interface for managing Agency.
 */
public interface AgencyRelationService {
	List<AgencyRelation> getListChiNhanhOrPGD(String parent, String type);
	
	AgencyRelation getById(String id);
}

