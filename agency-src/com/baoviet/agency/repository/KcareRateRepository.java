package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.KcareRate;


/**
 * Spring Data JPA repository for the GnocCr entity.
 */
@Repository
public interface KcareRateRepository extends JpaRepository<KcareRate, String> {
	
	List<KcareRate> findAllByAgeAndSexAndProgram(int age, String sex, String program);
}