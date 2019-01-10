package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Tl;


@Repository
public interface TlRepository extends JpaRepository<Tl, String> {
	
}