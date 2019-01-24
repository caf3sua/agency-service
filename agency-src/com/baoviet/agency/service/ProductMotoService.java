package com.baoviet.agency.service;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ProductMotoVM;
import com.baoviet.agency.web.rest.vm.PremiumMotoVM;

/**
 * Service Interface for managing Kcare.
 */
public interface ProductMotoService {

	ProductMotoVM createOrUpdatePolicy(ProductMotoVM moto, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	PremiumMotoVM calculatePremium(PremiumMotoVM param) throws AgencyBusinessException;
	
	ProductMotoVM createOrUpdatePolicyMOMO(ProductMotoVM moto, AgencyDTO currentAgency) throws AgencyBusinessException;
}

