package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.TlAnnualRate;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface TlAnnualRateRepository extends JpaRepository<TlAnnualRate, String> {
	TlAnnualRate findFirstByAgeFromLessThanEqualAndAgeToGreaterThanEqual(int age, int age1);
}