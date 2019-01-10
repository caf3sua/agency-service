package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Tvicare;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface TvicareRepository extends JpaRepository<Tvicare, String> {
	
}