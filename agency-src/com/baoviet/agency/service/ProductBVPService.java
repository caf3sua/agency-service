package com.baoviet.agency.service;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.PremiumBVPVM;
import com.baoviet.agency.web.rest.vm.ProductBvpVM;

/**
 * Service Interface for managing BVP.
 */
public interface ProductBVPService {

	ProductBvpVM createOrUpdatePolicy(ProductBvpVM bvp, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	PremiumBVPVM calculatePremium(PremiumBVPVM param) throws AgencyBusinessException;
	
	ProductBvpVM createOrUpdatePolicyAdayroi(ProductBvpVM bvp, AgencyDTO currentAgency, String loaidon, String ordercode) throws AgencyBusinessException;
}

