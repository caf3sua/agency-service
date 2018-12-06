package com.baoviet.agency.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	public Page<Contact> search(ContactSearchVM obj, String type) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM CONTACT WHERE type = :pType";
		if (!StringUtils.isEmpty(obj.getKeyWord())) {
			expression = expression +  " AND ( UPPER(CONTACT_NAME) LIKE '%" + obj.getKeyWord().toUpperCase() + "%' OR UPPER(EMAIL) LIKE '%" + obj.getKeyWord().toUpperCase() + "%' OR PHONE LIKE '%" + obj.getKeyWord() + "%' OR UPPER(ID_NUMBER) LIKE '%" + obj.getKeyWord().toUpperCase() + "%' )";
		} else {
			if (!StringUtils.isEmpty(obj.getContactName())) {
	        	expression = expression +  " AND UPPER(CONTACT_NAME) LIKE '%" + obj.getContactName().toUpperCase() + "%'";
	        }
	        if (!StringUtils.isEmpty(obj.getEmail())) {
	        	expression = expression +  " AND UPPER(EMAIL) LIKE '%" + obj.getEmail().toUpperCase() + "%'";
	        }
	        if (!StringUtils.isEmpty(obj.getPhone())) {
	        	expression = expression +  " AND PHONE LIKE '%" + obj.getPhone() + "%'";
	        } 
	        if (!StringUtils.isEmpty(obj.getIdNumber())) {
	        	expression = expression +  " AND UPPER(ID_NUMBER) LIKE '%" + obj.getIdNumber().toUpperCase() + "%'";
	        } 
	        if (obj.getDateOfBirth() != null) {
	        	expression = expression +  " AND to_char(DATE_OF_BIRTH, 'DD/MM/YYYY') = :pDoB";
	        }
	        if (!StringUtils.isEmpty(obj.getGroupType())) {
	        	expression = expression +  " AND GROUP_TYPE = :pGroup";
	        }
	        if (!StringUtils.isEmpty(obj.getCategoryType())) {
	        	expression = expression +  " AND CATEGORY_TYPE = :pCategoryType";
	        }
		}
        
        expression = expression +  " ORDER BY CONTACT_ID DESC";
        
        Query query = entityManager.createNativeQuery(expression, Contact.class);
        query.setParameter("pType", type);
        
        if (StringUtils.isEmpty(obj.getKeyWord())) {
        	if (obj.getDateOfBirth() != null) {
            	query.setParameter("pDoB", DateUtils.date2Str(obj.getDateOfBirth()));
            }
            if (!StringUtils.isEmpty(obj.getGroupType())) {
            	query.setParameter("pGroup", obj.getGroupType());
            }
            if (!StringUtils.isEmpty(obj.getCategoryType())) {
            	query.setParameter("pCategoryType", obj.getCategoryType());
            }	
        }
        
        // Paging
 		Pageable pageable = buildPageable(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
 		
        List<Contact> data = query.getResultList();
        long count = countSearchContact(obj, type);
		
		// Build pageable
        Page<Contact> dataPage = new PageImpl<>(data, pageable, count);
        
        return dataPage;
	}
	
	private long countSearchContact(ContactSearchVM obj, String type) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*) FROM CONTACT WHERE type = :pType";
        
		if (!StringUtils.isEmpty(obj.getKeyWord())) {
			expression = expression +  " AND ( UPPER(CONTACT_NAME) LIKE '%" + obj.getKeyWord().toUpperCase() + "%' OR UPPER(EMAIL) LIKE '%" + obj.getKeyWord().toUpperCase() + "%' OR PHONE LIKE '%" + obj.getKeyWord() + "%' )";
		} else {
			if (!StringUtils.isEmpty(obj.getContactName())) {
	        	expression = expression +  " AND UPPER(CONTACT_NAME) LIKE '%" + obj.getContactName().toUpperCase() + "%'";
	        }
	        if (!StringUtils.isEmpty(obj.getEmail())) {
	        	expression = expression +  " AND UPPER(EMAIL) LIKE '%" + obj.getEmail().toUpperCase() + "%'";
	        }
	        if (!StringUtils.isEmpty(obj.getPhone())) {
	        	expression = expression +  " AND PHONE LIKE '%" + obj.getPhone() + "%'";
	        } 
	        if (!StringUtils.isEmpty(obj.getIdNumber())) {
	        	expression = expression +  " AND UPPER(ID_NUMBER) LIKE '%" + obj.getIdNumber().toUpperCase() + "%'";
	        } 
	        if (obj.getDateOfBirth() != null) {
	        	expression = expression +  " AND to_char(DATE_OF_BIRTH, 'DD/MM/YYYY') = :pDoB";
	        }
	        if (!StringUtils.isEmpty(obj.getGroupType())) {
	        	expression = expression +  " AND GROUP_TYPE = :pGroup";
	        }
	        if (!StringUtils.isEmpty(obj.getCategoryType())) {
	        	expression = expression +  " AND CATEGORY_TYPE = :pCategoryType";
	        }
		}
        
        
        Query query = entityManager.createNativeQuery(expression);
        query.setParameter("pType", type);
        
        if (StringUtils.isEmpty(obj.getKeyWord())) {
        	if (obj.getDateOfBirth() != null) {
            	query.setParameter("pDoB", DateUtils.date2Str(obj.getDateOfBirth()));
            }
            if (!StringUtils.isEmpty(obj.getGroupType())) {
            	query.setParameter("pGroup", obj.getGroupType());
            }
            if (!StringUtils.isEmpty(obj.getCategoryType())) {
            	query.setParameter("pCategoryType", obj.getCategoryType());
            }	
        }
        
        BigDecimal data = (BigDecimal) query.getSingleResult();
        return data.longValue();
	}
	
	private Pageable buildPageable(ContactSearchVM obj) {
		Pageable pageable = null;
		if (obj.getPageable() == null) {
			return pageable;
		}
		
		String[] sorts = obj.getPageable().getSort().split(",");
		Sort sort = new Sort(Direction.fromString(sorts[1]), sorts[0]);
		pageable = new PageRequest(obj.getPageable().getPage(), obj.getPageable().getSize(), sort);
		return pageable;
	}

}