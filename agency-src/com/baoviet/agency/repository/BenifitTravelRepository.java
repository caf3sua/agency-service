package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.BenifitTravel;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface BenifitTravelRepository extends JpaRepository<BenifitTravel, String>, BenifitTravelRepositoryExtend {
	List<BenifitTravel> findByAreaId(String areaId);
}