package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.RePostcodeVn;


/**
 * Spring Data JPA repository for the GnocCR module.
 */


@Repository
public interface RePostcodeVnRepository extends JpaRepository<RePostcodeVn, String> {
	
	RePostcodeVn findByPkPostcode(String code);
}