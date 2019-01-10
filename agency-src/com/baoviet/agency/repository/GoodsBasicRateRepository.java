package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.GoodsBasicRate;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface GoodsBasicRateRepository extends JpaRepository<GoodsBasicRate, String> {
	GoodsBasicRate findByCategoryAndPackedTypeAndTransportAndOver500km	(int categoryId, int packedType, int transportId, int transportRange);
}