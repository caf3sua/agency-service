package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.ProductGenInfo;


/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface ProductGenInfoRepositoryExtend {
	
	List<ProductGenInfo> getByProductId(String productGenId); 
	
	List<ProductGenInfo> getProductGenInfosforhomepage(String site);
}