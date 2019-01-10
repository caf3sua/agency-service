package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.TvcPlaneAdd;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface TvcPlaneAddRepository extends JpaRepository<TvcPlaneAdd, String> {
	
	long deleteByTvcPlaneId(String id);
	
	List<TvcPlaneAdd> findByTvcPlaneId(String id);
}