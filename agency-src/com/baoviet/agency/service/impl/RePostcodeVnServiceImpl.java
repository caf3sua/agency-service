package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.dto.RePostcodeVnDTO;
import com.baoviet.agency.repository.RePostcodeVnRepository;
import com.baoviet.agency.service.RePostcodeVnService;
import com.baoviet.agency.service.mapper.RePostcodeVnMapper;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;


/**
 * Service Implementation for managing RePostcodeVn.
 * @author Đức, Lê Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class RePostcodeVnServiceImpl implements RePostcodeVnService {
	
	private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private RePostcodeVnRepository rePostcodeVnRepository;
	
	@Autowired
	private RePostcodeVnMapper rePostcodeVnMapper;
	
	
	@Override
	public List<RePostcodeVnDTO> getAll() {
		log.debug("REST request to getAll, RePostcodeVnDTO{}, ");
		return rePostcodeVnMapper.toDto(rePostcodeVnRepository.findAll());
	}


	@Override
	public RePostcodeVnDTO getAddressByCode(String code) {
		log.debug("REST request to getAddressByCode, code{} :", code);
		RePostcodeVnDTO data = rePostcodeVnMapper.toDto(rePostcodeVnRepository.findByPkPostcode(code));
		if (data != null && StringUtils.isNotEmpty(data.getPkProvince()) ) {
			data.setPkDistrict(data.getPkDistrict() + ", " + data.getPkProvince());
		}
		return data;
	}
}
