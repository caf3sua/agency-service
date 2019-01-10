package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.TravelCareAdd;


@Repository
public interface TravelCareAddRepository extends JpaRepository<TravelCareAdd, String> {
	List<TravelCareAdd> findByTravaelcareId(String tlId);
	
	long deleteByTravaelcareId(String id);
}