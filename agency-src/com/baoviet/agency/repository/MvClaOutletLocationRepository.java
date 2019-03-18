package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.MvClaOutletLocation;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface MvClaOutletLocationRepository extends JpaRepository<MvClaOutletLocation, String>{
	
	List<MvClaOutletLocation> findByOutletAmsId(String agentId);
	
	List<MvClaOutletLocation> findByPrOutletAmsId(String deparmentId);
	
	List<MvClaOutletLocation> findByPrOutletAmsIdAndOutletTypeCode(String deparmentId, String type);
	
	List<MvClaOutletLocation> findByOutletAmsIdAndPrOutletAmsId(String agentId, String deparmentId);
	
	List<MvClaOutletLocation> findByPrOutletAmsIdAndOutletNameLike(String deparmentId, String keyword);
}