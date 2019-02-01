package com.baoviet.agency.service;


import java.util.List;

import com.baoviet.agency.domain.ProductGenInfo;

/**
 * Service Interface for managing ProductGenService.
 */
public interface ProductGenService {
	
	List<ProductGenInfo> getProductInfo(String productCode);
	
	ProductGenInfo findByNewId(String newId);
	
	List<ProductGenInfo> getAll();
}

