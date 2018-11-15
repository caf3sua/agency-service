package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.ContactSearchVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class ContactRepositoryImpl implements ContactRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Contact> search(ContactSearchVM obj, String type) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM CONTACT WHERE type = :pType";
        if (!StringUtils.isEmpty(obj.getContactName())) {
        	expression = expression +  " AND CONTACT_NAME LIKE '%" + obj.getContactName() + "%'";
        } 
        if (!StringUtils.isEmpty(obj.getPhone())) {
        	expression = expression +  " AND PHONE = :pPhone";
        } 
        if (!StringUtils.isEmpty(obj.getIdNumber())) {
        	expression = expression +  " AND ID_NUMBER = :pIdNumber";
        } 
        if (obj.getDateOfBirth() != null) {
        	expression = expression +  " AND to_char(DATE_OF_BIRTH, 'DD/MM/YYYY') = :pDoB";
        }
        if (!StringUtils.isEmpty(obj.getGroupType())) {
        	expression = expression +  " AND GROUP_TYPE = :pGroup";
        }
        
        
        Query query = entityManager.createNativeQuery(expression, Contact.class);
        query.setParameter("pType", type);
        if (!StringUtils.isEmpty(obj.getPhone())) {
        	query.setParameter("pPhone", obj.getPhone());
        } 
        if (!StringUtils.isEmpty(obj.getIdNumber())) {
        	query.setParameter("pIdNumber", obj.getIdNumber());
        } 
        if (obj.getDateOfBirth() != null) {
        	query.setParameter("pDoB", DateUtils.date2Str(obj.getDateOfBirth()));
        }
        if (!StringUtils.isEmpty(obj.getGroupType())) {
        	query.setParameter("pGroup", obj.getGroupType());
        }
        
        List<Contact> data = query.getResultList();
        
        return data;
	}

}