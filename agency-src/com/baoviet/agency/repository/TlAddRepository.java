package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.TlAdd;


@Repository
public interface TlAddRepository extends JpaRepository<TlAdd, String> {
	List<TlAdd> findByTlId(String tlId);
	
	long deleteByTlId(String id);
}