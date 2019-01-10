package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AgencyRelation;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AgencyRelationRepository extends JpaRepository<AgencyRelation, String> {
	List<AgencyRelation> findByParrenetIdAndType(String parrenetId, String type);
}