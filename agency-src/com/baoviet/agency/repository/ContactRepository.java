package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>, ContactRepositoryExtend {
	
	List<Contact> findByEmail(String email);
	
	List<Contact> findByEmailIgnoreCase(String email);
	
	Contact findOneByUserName(String username);
	
	Contact findOneByContactCodeAndType(String contactCode, String type);
	
	List<Contact> findByType(String type);
	
	Contact findOneByContactCode(String contactCode);
	
	Contact findOneByContactUsername(String username);
	
	Contact findFirstByTypeOrderByContactIdDesc(String type);
	
	Contact findByContactIdAndType(String contactId, String type);
	
	Contact findOneByPhoneAndType(String phone, String type);
	Contact findOneByIdNumberAndType(String idNumber, String type);
	Contact findOneByEmailAndType(String email, String type);
}