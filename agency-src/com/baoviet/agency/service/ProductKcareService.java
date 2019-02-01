package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.domain.KcareBenefit;
import com.baoviet.agency.domain.KcareRate;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ProductKcareVM;
import com.baoviet.agency.web.rest.vm.PremiumKcareVM;

/**
 * Service Interface for managing Kcare.
 */
public interface ProductKcareService {

	List<KcareBenefit> getAllBenefit();
	
	KcareRate getAllKcareRateByParams(int age, String sex, String program) throws AgencyBusinessException;
	
	ProductKcareVM createOrUpdatePolicy(AgencyDTO currentAgency, ProductKcareVM kcare) throws AgencyBusinessException;
	
	PremiumKcareVM calculatePremium(PremiumKcareVM param) throws AgencyBusinessException;
}

