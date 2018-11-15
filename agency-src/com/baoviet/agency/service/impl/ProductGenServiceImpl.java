package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.ProductGen;
import com.baoviet.agency.domain.ProductGenInfo;
import com.baoviet.agency.repository.ProductGenInfoRepository;
import com.baoviet.agency.repository.ProductGenRepository;
import com.baoviet.agency.service.ProductGenService;

/**
 * Service Implementation for managing GnocCr.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class ProductGenServiceImpl implements ProductGenService {

	private final Logger log = LoggerFactory.getLogger(ProductGenServiceImpl.class);

	@Autowired
	private ProductGenRepository productGenRepository;
	
	@Autowired
	private ProductGenInfoRepository productGenInfoRepository;
	
	@Override
	public List<ProductGenInfo> getProductInfo(String productCode) {
		log.debug("Request to getProductInfo, {}", productCode);

		if (!StringUtils.isEmpty(productCode)) {
			ProductGen productGen = productGenRepository.findByFkProductId(productCode);
			if(productGen != null) {
				List<ProductGenInfo> lstProductGenInfo = productGenInfoRepository.getByProductId(productGen.getProductGenId());
				return lstProductGenInfo;
			}
		}
		return null;
	}

	@Override
	public ProductGenInfo findByNewId(String newId) {
		log.debug("Request to findByNewId, {}", newId);
		ProductGenInfo data = productGenInfoRepository.findOne(newId);
		return data;
	}

	@Override
	public List<ProductGenInfo> getAll() {
		List<ProductGenInfo> data = productGenInfoRepository.findAll();
		if (data != null && data.size() > 0) {
			return data;
		}
		return null;
	}

}
