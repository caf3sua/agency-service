package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Anchi;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AnchiRepository extends JpaRepository<Anchi, String>, AnchiRepositoryExtend {
	
	List<Anchi> findByStatusAndAchiDonvi(String statusId, String achiDonvi);
	
	Anchi findByAchiSoAnchiAndAchiDonvi(String soAnchi, String achiDonvi);
}