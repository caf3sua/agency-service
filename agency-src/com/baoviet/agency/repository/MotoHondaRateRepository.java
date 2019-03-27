package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.MotoHondaRate;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface MotoHondaRateRepository extends JpaRepository<MotoHondaRate, String> {
	MotoHondaRate findBySotienFromLessThanEqualAndSotienToGreaterThanEqualAndSoNamAndGoi(Double sotien1, Double sotien2, String soNam, String goi);
	
}