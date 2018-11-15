package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.ContactRelationship;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AgentContactRelationshipRepository extends JpaRepository<ContactRelationship, String> {
	
	long deleteByContactId(String contactId);
	
	List<ContactRelationship> findByContactId(String contactId);
}