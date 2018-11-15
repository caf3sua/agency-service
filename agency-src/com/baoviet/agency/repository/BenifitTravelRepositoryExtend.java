package com.baoviet.agency.repository;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.BenifitTravel;


/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface BenifitTravelRepositoryExtend {
	
	BenifitTravel getByParam(int numberOfDays, String type, String area, String planId); 
}