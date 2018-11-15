package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.PayActionDTO;
import com.baoviet.agency.repository.PayActionRepository;
import com.baoviet.agency.service.PayActionService;
import com.baoviet.agency.service.mapper.PayActionMapper;

/**
 * Service Implementation for managing Payment.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class PayActionServiceImpl implements PayActionService {

	private final Logger log = LoggerFactory.getLogger(PayActionServiceImpl.class);

	@Autowired
	private PayActionRepository payActionRepository;

	@Autowired
	private PayActionMapper payActionMapper;

	@Override
	public String save(PayAction info) {
		log.debug("Request to insert PayAction, {}", info);

		PayAction result = payActionRepository.save(info);
		return String.valueOf(result.getPayActionId());
	}

	@Override
	public List<PayActionDTO> search(String fromDate, String toDate, String agentId) {
		log.debug("Request to search PayAction, fromDate{}, toDate{}, agentId{} : ", fromDate, toDate, agentId);
		List<PayAction> entity = payActionRepository.search(fromDate, toDate, agentId);
		return payActionMapper.toDto(entity);
	}
	
	@Override
	public List<PayActionDTO> searchAdmin(String fromDate, String toDate, String admin) {
		log.debug("Request to search PayAction, fromDate{}, toDate{}, admin{} : ", fromDate, toDate, admin);
		List<PayAction> entity = payActionRepository.searchAdmin(fromDate, toDate, admin);
		return payActionMapper.toDto(entity);
	}

	@Override
	public List<PayActionDTO> search(String contactId, String agentId) {
		log.debug("Request to search PayAction, contactId{}, agentId{} : ", contactId, agentId);
		List<PayAction> entity = payActionRepository.search(contactId, agentId);
		return payActionMapper.toDto(entity);
	}
}
