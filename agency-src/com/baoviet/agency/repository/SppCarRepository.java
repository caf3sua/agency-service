package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.SppCar;


@Repository
public interface SppCarRepository extends JpaRepository<SppCar, String> {
	
}