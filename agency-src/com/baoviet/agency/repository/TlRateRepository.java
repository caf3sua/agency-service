package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.TlRate;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface TlRateRepository extends JpaRepository<TlRate, String> {
	TlRate findByFrommonthLessThanEqualAndTomonthGreaterThanEqual(Integer month, Integer month1);
}