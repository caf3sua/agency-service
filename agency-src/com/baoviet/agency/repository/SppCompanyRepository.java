package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.SppCompany;

/**
 * Spring Data JPA repository for the SppCompany module.
 */

@Repository
public interface SppCompanyRepository extends JpaRepository<SppCompany, String> {
	SppCompany findByCompanyCode(String companyCode);
}