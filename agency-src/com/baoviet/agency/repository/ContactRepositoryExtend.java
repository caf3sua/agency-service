package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.web.rest.vm.ContactSearchVM;


/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface ContactRepositoryExtend {
	
	List<Contact> search(ContactSearchVM obj, String type);
}