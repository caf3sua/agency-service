package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Travelcare;


@Repository
public interface TravelcareRepository extends JpaRepository<Travelcare, String> {
	
}