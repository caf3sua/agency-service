package com.baoviet.agency.service;


import com.baoviet.agency.domain.CarRate;

/**
 * Service Interface for managing TVC.
 */
public interface CarRateService {
	
	CarRate getCarRateByParam(String mucdich, Integer socho, String type);
	
	CarRate getCarRateByParam(String mucdich, Integer socho, String type,String agencyid);
}

