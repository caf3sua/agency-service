package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.CarRate;

@Repository
public interface CarRateRepository extends JpaRepository<CarRate, String> {
	List<CarRate> findBySeatNumberFromLessThanEqualAndSeatNumberToGreaterThanEqualAndPurposeOfUsageIdAndType(Integer socho1, Integer socho2, String purposeOfUsageId, String type);
	
	List<CarRate> findBySeatNumberFromLessThanEqualAndSeatNumberToGreaterThanEqualAndPurposeOfUsageIdAndTypeAndAgencyId(Integer socho1, Integer socho2, String purposeOfUsageId, String type, String agencyId);
}