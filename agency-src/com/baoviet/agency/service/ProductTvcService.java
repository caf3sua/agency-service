package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.domain.BenifitTravel;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.PremiumTvcVM;
import com.baoviet.agency.web.rest.vm.ProductTvcVM;

/**
 * Service Interface for managing TVC.
 */
public interface ProductTvcService {

	ProductTvcVM createOrUpdatePolicy(ProductTvcVM tnc, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	PremiumTvcVM calculatePremium(PremiumTvcVM param) throws AgencyBusinessException;
	
	List<BenifitTravel> getBenefitByAreaId(String areaId) throws AgencyBusinessException;
}

