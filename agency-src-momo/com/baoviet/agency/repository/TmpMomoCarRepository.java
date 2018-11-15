package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.TmpMomoCar;

@Repository
public interface TmpMomoCarRepository extends JpaRepository<TmpMomoCar, String> {
	TmpMomoCar findByRequestId(String requestId);
	
	TmpMomoCar findByGycbhNumber(String gycbhNumber);
}