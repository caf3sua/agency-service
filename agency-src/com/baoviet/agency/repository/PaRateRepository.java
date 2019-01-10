package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.PaRate;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface PaRateRepository extends JpaRepository<PaRate, String> {
	PaRate findOneByFrommonthLessThanEqualAndTomonthGreaterThanEqual(Integer frommount, Integer tomount);
}