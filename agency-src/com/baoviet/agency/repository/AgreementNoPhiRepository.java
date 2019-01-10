package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AgreementNoPhi;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AgreementNoPhiRepository extends JpaRepository<AgreementNoPhi, String> {
	
	AgreementNoPhi findByAgreementIdAndContactId(String agreementId, String contactId);
}