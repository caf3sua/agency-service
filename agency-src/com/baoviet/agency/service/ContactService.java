package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.dto.ContactDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ContactCreateVM;
import com.baoviet.agency.web.rest.vm.ContactSearchVM;
import com.baoviet.agency.web.rest.vm.ContactUpdateVM;

/**
 * Service Interface for managing Contact.
 */
public interface ContactService {
	
	ContactDTO save(ContactDTO contactDTO);
	
	ContactDTO findOne(String contactId, String type);
	
	void delete(String contactId);
	
	List<ContactDTO> findAll();
	
	List<ContactDTO> findByEmail(String email);
	
	List<ContactDTO> findByEmailIgnoreCase(String email);
	
	ContactDTO findOneByUserName(String username);
	
	ContactDTO findOneByContactCodeAndType(String contactCode, String type);
	
//	List<ContactDTO> search(ContactDTO contact);
	
//	ContactDTO findOneByContactCode(String contactCode);
	
	ContactDTO findOneByContactUsername(String email);
	
	List<ContactDTO> searchContact(ContactSearchVM contact, String type);
	
	List<ContactDTO> findAllByType(String type);
	
	String generateContactCode(String type);
	
	ContactDTO create(ContactDTO contactDTO, ContactCreateVM param) throws AgencyBusinessException;
	
	ContactDTO update(ContactUpdateVM param, String type) throws AgencyBusinessException;
	
	ContactDTO findOneByPhoneAndType(String phone, String type);
	
	ContactUpdateVM convertContactToVM(ContactDTO contactDTO, String type);
}

