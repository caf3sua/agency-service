package com.baoviet.agency.service;

import java.io.IOException;
import java.net.URISyntaxException;

import com.baoviet.agency.dto.AgreementSearchDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ProductBaseVM;

/**
 * Service Interface for managing Kcare.
 */
public interface ProductCommonService {

	String getProductIdByGycbhId(String lineId, String gycbhId);
	
	void addProductObject(String lineId, String gycbhId, AgreementSearchDTO result);
	
	<T extends ProductBaseVM> T convertProductObjectToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException, IOException, Exception;	// dùng để check xem tái tục hay ko. 1: Có
}

