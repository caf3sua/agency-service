package com.baoviet.agency.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AgentReminder;
import com.baoviet.agency.service.impl.AgentReminderServiceImpl;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.ReminderSearchVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class AgentReminderRepositoryImpl implements AgentReminderRepositoryExtend {

	private final Logger log = LoggerFactory.getLogger(AgentReminderServiceImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<AgentReminder> searchReminder(ReminderSearchVM param, String type) {
		log.debug("Request to searchReminder : ReminderSearchVM {}, type {} ", param, type);

		String expression = "SELECT * FROM AGENT_REMINDER WHERE type = :pType";
        if (!StringUtils.isEmpty(param.getContactId())) {
        	expression = expression +  " AND CONTACT_ID = :pContactId";
        } 
        if (!StringUtils.isEmpty(param.getProductCode())) {
        	expression = expression +  " AND PRODUCT_CODE = :pProductCode";
        } 
        if (param.getFromDate() != null) {
        	expression = expression +  " AND REMINDE_DATE >= :pFromDate";
        } 
        if (param.getToDate() != null) {
        	expression = expression +  " AND REMINDE_DATE <= :pToDate";
        }
        if (!StringUtils.isEmpty(param.getActive())) {
        	expression = expression +  " AND ACTIVE = :pActive";
        }
		
        Query query = entityManager.createNativeQuery(expression, AgentReminder.class);
        query.setParameter("pType", type);
        
        if (!StringUtils.isEmpty(param.getContactId())) {
        	query.setParameter("pContactId", param.getContactId());
        } 
        if (!StringUtils.isEmpty(param.getProductCode())) {
        	query.setParameter("pProductCode", param.getProductCode());
        } 
        if (param.getFromDate() != null) {
        	query.setParameter("pFromDate", param.getFromDate());
        }
        if (param.getToDate() != null) {
        	query.setParameter("pToDate", param.getToDate());
        }
        if (!StringUtils.isEmpty(param.getActive())) {
        	query.setParameter("pActive", param.getActive());
        }
        
        List<AgentReminder> data = query.getResultList();
        log.debug("Request to searchReminder : data {} ", data.size());
        return data;
	}

	@Override
	public List<AgentReminder> getCountReminder(String type, Integer numberDay) {
		Date newDate = new Date();
		Date dateTo = DateUtils.addDay(newDate, numberDay);
		String expression = "SELECT * FROM AGENT_REMINDER WHERE type = :pType AND REMINDE_DATE >= :pDateFrom AND REMINDE_DATE <= :pDateTo";
        Query query = entityManager.createNativeQuery(expression, AgentReminder.class);
        query.setParameter("pType", type);
        query.setParameter("pDateFrom", newDate);
        query.setParameter("pDateTo", dateTo);
        
        List<AgentReminder> data = query.getResultList();
        log.debug("Request to getCountReminder : data {} ", data.size());
        return data;
	}
}