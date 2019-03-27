package com.baoviet.agency.service;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ProductHondaVM;

/**
 * Service Interface for managing Kcare.
 */
public interface ProductHondaService {

	ProductHondaVM createOrUpdatePolicy(ProductHondaVM moto, AgencyDTO currentAgency) throws AgencyBusinessException;
}

