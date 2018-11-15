package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.PurposeOfUsage;


@Repository
public interface PurposeOfUsageRepository extends JpaRepository<PurposeOfUsage, String> {
	
	List<PurposeOfUsage> findByCategoryId(String categoryId);
	
	List<PurposeOfUsage> findByCategoryIdAndSeatNumber(String categoryId, Integer seatNumber);
}