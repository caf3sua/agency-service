package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.domain.BenifitTvi;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.PremiumTviVM;
import com.baoviet.agency.web.rest.vm.ProductTviVM;

/**
 * Service Interface for managing TVI.
 */
public interface ProductTviService {

	ProductTviVM createOrUpdatePolicy(ProductTviVM tnc, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	PremiumTviVM calculatePremium(PremiumTviVM param) throws AgencyBusinessException;
	
	List<BenifitTvi> getAllBenefit();
}

