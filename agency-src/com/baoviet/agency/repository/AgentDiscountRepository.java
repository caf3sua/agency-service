package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AgentDiscount;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AgentDiscountRepository extends JpaRepository<AgentDiscount, String> {
	
	AgentDiscount findByAgencyIdAndLineId(String agencyId, String lineId);
	
}