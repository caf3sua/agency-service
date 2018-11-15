package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.ContactProduct;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface ContactProductRepository extends JpaRepository<ContactProduct, String> {
	
	long deleteByContactId(String contactId);
	
	List<ContactProduct> findByContactId(String contactId);
}