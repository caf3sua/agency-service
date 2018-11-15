package com.baoviet.agency.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class CodeManagementRepositoryImpl implements CodeManagementRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;
	
}