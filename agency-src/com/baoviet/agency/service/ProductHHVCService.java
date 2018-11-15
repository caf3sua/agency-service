package com.baoviet.agency.service;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ProductHhvcVM;
import com.baoviet.agency.web.rest.vm.PremiumHHVCVM;

/**
 * Service Interface for managing HHVC.
 */
public interface ProductHHVCService {

	ProductHhvcVM createOrUpdateHhvcPolicy(ProductHhvcVM tnc, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	PremiumHHVCVM calculatePremium(PremiumHHVCVM param) throws AgencyBusinessException;
}

