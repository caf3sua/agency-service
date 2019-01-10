package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.TinhtrangSk;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface TinhtrangSkRepository extends JpaRepository<TinhtrangSk, String> {
	
	List<TinhtrangSk> findByIdThamchieuAndMasanpham(String id, String maSp);
	
	long deleteByIdThamchieu(String id);
}