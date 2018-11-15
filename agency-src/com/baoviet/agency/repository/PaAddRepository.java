package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.PaAdd;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface PaAddRepository extends JpaRepository<PaAdd, String> {
	
	List<PaAdd> findByPaId(String id);
	
	long deleteByPaId(String id);
}