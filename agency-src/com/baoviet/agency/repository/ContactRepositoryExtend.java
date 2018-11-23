package com.baoviet.agency.repository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.web.rest.vm.ContactSearchVM;


/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface ContactRepositoryExtend {
	
	Page<Contact> search(ContactSearchVM obj, String type);
}