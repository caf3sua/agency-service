package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.ContactProduct;
import com.baoviet.agency.dto.ContactProductDTO;
import com.baoviet.agency.repository.ContactProductRepository;
import com.baoviet.agency.service.ContactProductService;
import com.baoviet.agency.service.mapper.ContactProductMapper;

/**
 * Service Implementation for managing GnocCr.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
public class ContactProductServiceImpl implements ContactProductService {

	private final Logger log = LoggerFactory.getLogger(ContactProductServiceImpl.class);

	@Autowired
	private ContactProductRepository contactProductRepository;

	@Autowired
	private ContactProductMapper contactProductMapper;

	@Override
	public ContactProductDTO save(ContactProductDTO info) {
		log.debug("Request to save ContactProduct : {}", info);

		ContactProduct contact = contactProductMapper.toEntity(info);
		contact = contactProductRepository.save(contact);
		return contactProductMapper.toDto(contact);
	}

}
