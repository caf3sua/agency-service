package com.baoviet.agency.service;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ProductKhcVM;
import com.baoviet.agency.web.rest.vm.KhcResultVM;
import com.baoviet.agency.web.rest.vm.PremiumKhcVM;

/**
 * Service Interface for managing Khc.
 */
public interface ProductKhcService {

	KhcResultVM createOrUpdatePolicy(ProductKhcVM param, AgencyDTO currentAgency) throws AgencyBusinessException;

	PremiumKhcVM calculatePremium(PremiumKhcVM param) throws AgencyBusinessException;
}

