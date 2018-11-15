package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Car;


@Repository
public interface CarRepository extends JpaRepository<Car, String>, CarRepositoryExtend {
	Car findBySoGycbh(String soGycbh);
}