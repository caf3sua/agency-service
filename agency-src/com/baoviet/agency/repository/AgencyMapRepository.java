package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AgencyMap;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AgencyMapRepository extends JpaRepository<AgencyMap, String> {
	
	List<AgencyMap> findByAgencyP3Id(String id);
	
	List<AgencyMap> findByBvId3(String id);
}