package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.service.impl.AgentReminderServiceImpl;
import com.baoviet.agency.web.rest.vm.SearchPrintedPaperVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class AnchiRepositoryImpl implements AnchiRepositoryExtend {

	private final Logger log = LoggerFactory.getLogger(AgentReminderServiceImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Anchi> search(SearchPrintedPaperVM param, String type) {
		log.debug("Request to search : SearchPrintedPaperVM {}, type {} ", param, type);
		String expression = "SELECT ANCHI_ID, ACHI_SO_ANCHI, ACHI_HD_ID, ACHI_TUNGAY, ACHI_DENNGAY, ACHI_TINHTRANG_CAP, ACHI_NGAYCAP, ACHI_PHIBH, ACHI_STIENVN, null as IMG_GCN, null as IMG_GYCBH, null as IMG_HD, MCI_ADD_ID, " + 
				" STATUS, POLICY_NUMBER, QLHA_ID, INSUREJ_URN, BVSYSDATE, CREATE_USER, MODIFY_DATE, MODIFY_USER, ACHI_MA_ANCHI, ACHI_TEN_ANCHI, ACHI_DONVI, LINE_ID, CONTACT_ID FROM ANCHI WHERE ACHI_DONVI = :pType";
		
		if (!StringUtils.isEmpty(param.getUrn())) {
        	expression = expression +  " AND MCI_ADD_ID LIKE '%" + param.getUrn() + "%'";
        }
		if (!StringUtils.isEmpty(param.getNumber())) {
        	expression = expression +  " AND ACHI_SO_ANCHI LIKE '%" + param.getNumber() + "%'";
        }
		if (!StringUtils.isEmpty(param.getGycbhNumber())) {
        	expression = expression +  " AND POLICY_NUMBER LIKE '%" + param.getGycbhNumber() + "%'";
        }
		if (!StringUtils.isEmpty(param.getType())) {
        	expression = expression +  " AND LINE_ID = :pLoaiAnchi";
        }
		
		Query query = entityManager.createNativeQuery(expression, Anchi.class);
        query.setParameter("pType", type);
        if (!StringUtils.isEmpty(param.getType())) {
        	query.setParameter("pLoaiAnchi", param.getType());	
        }
		List<Anchi> data = query.getResultList();
        
		log.debug("Request to searchReminder : data {} ", data.size());
        if (data != null && data.size() > 0) {
        	return data;
        }
        
		return null;
	}

}