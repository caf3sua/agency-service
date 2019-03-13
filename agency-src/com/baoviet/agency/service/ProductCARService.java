package com.baoviet.agency.service;

import java.io.IOException;
import java.util.List;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.SppCarDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.PremiumCARVM;
import com.baoviet.agency.web.rest.vm.ProductCarImageVM;
import com.baoviet.agency.web.rest.vm.ProductCarVM;

/**
 * Service Interface for managing TVC.
 */
public interface ProductCARService {

	ProductCarVM createOrUpdatePolicy(ProductCarVM obj, AgencyDTO currentAgency) throws AgencyBusinessException, IOException;
	
	PremiumCARVM calculatePremium(PremiumCARVM param, String agencyRole) throws AgencyBusinessException;
	
	String getMinManufactureYear(String carId) throws AgencyBusinessException;
	
	String getMaxManufactureYear(String carId) throws AgencyBusinessException;
	
	String getCarPriceWithYear(String carId,Integer year) throws AgencyBusinessException;
	
	List<SppCarDTO> getCarInfo() throws AgencyBusinessException;
	
	List<String> getCarMakes() throws AgencyBusinessException;
	
	List<SppCarDTO> getCarModel(String model) throws AgencyBusinessException;
	
	List<String> getAllYear() throws AgencyBusinessException;
	
	ProductCarImageVM updateImagesPolicy(ProductCarImageVM obj, AgencyDTO currentAgency) throws AgencyBusinessException, IOException;
}

