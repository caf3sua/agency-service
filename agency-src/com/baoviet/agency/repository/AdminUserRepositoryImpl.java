package com.baoviet.agency.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.domain.AgentReminder;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.service.impl.AgentReminderServiceImpl;
import com.baoviet.agency.service.mapper.AgencyMapper;
import com.baoviet.agency.web.rest.vm.AdminSearchAgencyVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class AdminUserRepositoryImpl implements AdminUserRepositoryExtend {

	private final Logger log = LoggerFactory.getLogger(AgentReminderServiceImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private AgencyMapper agencyMapper;

	@Override
	public List<AgencyDTO> searchAgency(AdminSearchAgencyVM param, String adminId) {
		log.debug("Request to searchAgency : AdminSearchAgencyVM {} ", param);

		String expression1 = "SELECT OL.OUTLET_AMS_ID ma, OL.OUTLET_NAME ten FROM MV_CLA_OUTLET_LOCATION OL WHERE OL.PR_OUTLET_AMS_ID  IN (SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = '"+ adminId +"') AND OL.OUTLET_AMS_ID IS NOT NULL";
		
		String expression2 = "SELECT AA.AGENT_CODE ma, AA.AGENT_NAME ten FROM MV_AGENT_AGREEMENT AA WHERE AA.DEPARTMENT_CODE IN (SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = '"+ adminId +"') AND AA.AGENT_CODE IS NOT NULL";
		
        if (!StringUtils.isEmpty(param.getDepartmentId())) {
        	expression1 = expression1 +  " AND OL.PR_OUTLET_AMS_ID = '"+ param.getDepartmentId() +"' ";
        	expression2 = expression2 +  " AND AA.DEPARTMENT_CODE = '"+ param.getDepartmentId() +"' ";
        } 
        
        String expression = expression1 + " UNION " + expression2;
		
        Query query = entityManager.createNativeQuery(expression);
        
        List<Object[]> data = query.getResultList();
        log.debug("Request to searchAgency : data {} ", data.size());
        if (data != null && data.size() > 0) {
        	return convertToDTO(data, param);
        }
        return null;
	}

	private List<AgencyDTO> convertToDTO(List<Object[]> data, AdminSearchAgencyVM param) {
		List<AgencyDTO> lstAgency = new ArrayList<>();
		List<AgencyDTO> result = new ArrayList<>();
		for (Object[] item : data) {
			AgencyDTO agency = new AgencyDTO();
			agency.setId(item[0].toString());
			agency.setMa(item[0].toString());
			agency.setTen(item[1].toString());
			lstAgency.add(agency);
		}
		if (param.getNumberRecord() != null && param.getNumberRecord() > 0) {
			for (int i = 0; i < param.getNumberRecord(); i++) {
				result.add(lstAgency.get(i));
			}
		} else {
			return lstAgency;
		}
		
		return result;
	}
}