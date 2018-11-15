package com.baoviet.agency.repository;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.BenifitTvi;


/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface BenifitTviRepositoryExtend {
	
	BenifitTvi getByParam(int numberOfDays, String type, String area, String planId); 
}