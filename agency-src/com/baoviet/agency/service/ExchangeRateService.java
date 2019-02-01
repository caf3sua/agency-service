package com.baoviet.agency.service;


import java.util.List;

import com.baoviet.agency.bean.ExchangeRateDTO;

/**
 * Service Interface for managing TVC.
 */
public interface ExchangeRateService {
	
	List<ExchangeRateDTO> getAllExchangeRateDataFromVCB();
	
	double getCurrentRate(String currencyCode1, String currencyCode2);
}

