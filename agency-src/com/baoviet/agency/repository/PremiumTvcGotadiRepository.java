package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.PremiumTvcGotadi;

@Repository
public interface PremiumTvcGotadiRepository extends JpaRepository<PremiumTvcGotadi, String> {
	PremiumTvcGotadi findByAreaIdAndPlanIdAndTypeOfAgencyAndFromDateLessThanEqualAndToDateGreaterThanEqual(String areaId, String planId, String typeOfAgency,  Integer songay1, Integer songay2);
}