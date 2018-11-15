package com.baoviet.agency.adayroi.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.baoviet.agency.adayroi.dto.ConfirmOrder;
import com.baoviet.agency.adayroi.dto.Order;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Service Interface for managing Agency.
 */
public interface AdayroiService {

	String getAccessToken();
	
	List<Order> getBvpProduct(String token) throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException;
	
	
	void confirmPurchaseOrder(String accessToken, ConfirmOrder order) throws JsonProcessingException;
	
	List<Order> createBvpProduct(List<Order> lstOrder, AgencyDTO currentAgency, String token) throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException;
}

