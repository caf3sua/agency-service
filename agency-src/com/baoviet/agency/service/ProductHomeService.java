package com.baoviet.agency.service;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ProductHomeVM;
import com.baoviet.agency.web.rest.vm.PremiumHomeVM;

/**
 * Service Interface for managing Home.
 */
public interface ProductHomeService {

	ProductHomeVM createOrUpdatePolicy(ProductHomeVM param, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	PremiumHomeVM calculatePremium(PremiumHomeVM param) throws AgencyBusinessException;
}

