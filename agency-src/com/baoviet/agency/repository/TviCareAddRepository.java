package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.TviCareAdd;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface TviCareAddRepository extends JpaRepository<TviCareAdd, String> {
	
	List<TviCareAdd> findByTravaelcareId(String travaelcareId);
	
	long deleteByTravaelcareId(String id);
}