package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.GrabGiftCode;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface GrabGiftCodeRepository extends JpaRepository<GrabGiftCode, String>, GrabGiftCodeRepositoryExtend {
	
}