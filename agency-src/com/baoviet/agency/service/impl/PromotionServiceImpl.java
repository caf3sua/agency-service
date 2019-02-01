package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.PromotionBank;
import com.baoviet.agency.repository.PromotionBankRepository;
import com.baoviet.agency.service.PromotionBankService;


/**
 * Service Implementation for managing Promotion.
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "promotionBank")
public class PromotionServiceImpl implements PromotionBankService {
	
	private final Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);
	
	@Autowired
	private PromotionBankRepository promotionBankRepository;
	
	@Override
	public List<PromotionBank> getAll() {
		log.debug("REST request to getAllPromotionBank");
		List<PromotionBank> promotionBanks = promotionBankRepository.findByActive("1");
		if(promotionBanks != null && promotionBanks.size() > 0) {
			return promotionBanks;
		}
		return null;
	}
	
}
