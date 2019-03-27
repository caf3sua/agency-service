package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.MotoHondaCat;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface MotoHondaCatRepository extends JpaRepository<MotoHondaCat, String> {
	List<MotoHondaCat> findBySoNam(String id);
}