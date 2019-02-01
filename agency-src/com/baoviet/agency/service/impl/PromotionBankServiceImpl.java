package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Promotion;
import com.baoviet.agency.repository.PromotionRepository;
import com.baoviet.agency.service.PromotionService;
import com.baoviet.agency.web.rest.PromotionResource;


/**
 * Service Implementation for managing Home.
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class PromotionBankServiceImpl implements PromotionService {
	
	private final Logger log = LoggerFactory.getLogger(PromotionResource.class);
	
	@Autowired
	private PromotionRepository promotionRepository;
	
	@Override
	public List<Promotion> getAll() {
		log.debug("REST request to getPromosAll");
		List<Promotion> lstPromotion = promotionRepository.findByActive("1");
		if(lstPromotion !=null && lstPromotion.size() > 0) {
			return lstPromotion;
		}
		return null;
	}
	
}
