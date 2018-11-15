package com.baoviet.agency.service;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.HastableTNC;
import com.baoviet.agency.web.rest.vm.PremiumTncVM;
import com.baoviet.agency.web.rest.vm.ProductTncVM;

/**
 * Service Interface for managing Kcare.
 */
public interface ProductTncService {

	HastableTNC createOrUpdatePolicy(ProductTncVM tnc, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	PremiumTncVM calculatePremium(PremiumTncVM param) throws AgencyBusinessException;
}

