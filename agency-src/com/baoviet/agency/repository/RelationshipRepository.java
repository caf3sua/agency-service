package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Relationship;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, String>{
	List<Relationship> findByRelationshipName(String relationshipName);
	Relationship findTopByRelationshipName(String relationshipName);
}