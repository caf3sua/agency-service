package com.baoviet.agency.service.impl;

import java.net.URISyntaxException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.AgreementNoPhi;
import com.baoviet.agency.dto.AgreementNoPhiDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgreementNoPhiRepository;
import com.baoviet.agency.repository.AgreementRepository;
import com.baoviet.agency.service.AgreementNoPhiService;
import com.baoviet.agency.service.mapper.AgreementNoPhiMapper;

/**
 * Service Implementation for managing AgreementNoPhi.
 * 
 * @author Đức, Lê Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class AgreementNoPhiServiceImpl implements AgreementNoPhiService {

	private final Logger log = LoggerFactory.getLogger(AgreementNoPhiServiceImpl.class);
	
	@Autowired
	private AgreementNoPhiRepository agreementNoPhiRepository;
	
	@Autowired
	private AgreementNoPhiMapper agreementNoPhiMapper;
	
	@Autowired
	private AgreementRepository agreementRepository;
	
	@Override
	public AgreementNoPhiDTO save(AgreementNoPhiDTO param) {
		log.debug("Request to save agreementNoPhi, {}", param);
		// Convert to Entity
		AgreementNoPhi entity = agreementNoPhiMapper.toEntity(param);
		AgreementNoPhi result = agreementNoPhiRepository.save(entity);
		return agreementNoPhiMapper.toDto(result);
	}

	@Override
	public AgreementNoPhiDTO getByAgreementId(String agreementId, String contactId) {
		log.debug("Request to getByAgreementId - AgreementNoPhiDTO, agreementId {}, contactId {}", agreementId, contactId);
		AgreementNoPhi result = agreementNoPhiRepository.findByAgreementIdAndContactId(agreementId, contactId);
		if (result != null) {
			return agreementNoPhiMapper.toDto(result);	
		}
		return null;
	}

	@Override
	public void delete(String type, String id) throws URISyntaxException, AgencyBusinessException {
		log.debug("Request to delete agreement no phi by id, {}", id);
		AgreementNoPhi data = agreementNoPhiRepository.findOne(id);
		if (data != null) {
			Agreement agree = agreementRepository.findByAgreementIdAndAgentId(data.getAgreementId(), type);
			if (agree != null) {
				agreementNoPhiRepository.delete(id);	
			} else {
				throw new AgencyBusinessException("id", ErrorCode.INVALID, "Id không tồn tại");
			}
		} else {
			throw new AgencyBusinessException("id", ErrorCode.INVALID, "Id không tồn tại");
		}
		
	}

}
