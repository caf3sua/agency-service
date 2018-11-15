package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.KcareBenefit;


/**
 * Spring Data JPA repository for the GnocCr entity.
 */
@Repository
public interface KcareBenefitRepository extends JpaRepository<KcareBenefit, String> {
	
}