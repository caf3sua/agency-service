package com.baoviet.agency.service;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.PremiumTvcPlaneVM;
import com.baoviet.agency.web.rest.vm.ProductTvcPlaneVM;

/**
 * Service Interface for managing Home.
 */
public interface ProductTvcPlaneService {

	PremiumTvcPlaneVM calculatePremium(PremiumTvcPlaneVM param) throws AgencyBusinessException;

	ProductTvcPlaneVM createOrUpdatePolicy(ProductTvcPlaneVM param, AgencyDTO currentAgency) throws AgencyBusinessException;

}
