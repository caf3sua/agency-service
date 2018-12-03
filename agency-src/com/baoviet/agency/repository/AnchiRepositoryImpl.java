package com.baoviet.agency.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AnchiDTO;
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
	public List<AnchiDTO> search(SearchPrintedPaperVM param, String type) {
		log.debug("Request to search : SearchPrintedPaperVM {}, type {} ", param, type);
		String expression = "SELECT ac.MCI_ADD_ID, AC.ACHI_SO_ANCHI, AC.LINE_ID, ac.ACHI_TEN_ANCHI, AC.ACHI_TUNGAY, ac.ACHI_PHIBH, AC.ACHI_STIENVN, ac.POLICY_NUMBER, a.STATUS_POLICY_ID" + 
				" FROM ANCHI ac" + 
				" INNER JOIN AGREEMENT a ON a.policy_number = ac.policy_number" + 
				" WHERE ac.ACHI_DONVI = :pType";
		
		if (!StringUtils.isEmpty(param.getUrn())) {
        	expression = expression +  " AND ac.MCI_ADD_ID LIKE '%" + param.getUrn() + "%'";
        }
		if (!StringUtils.isEmpty(param.getNumber())) {
        	expression = expression +  " AND ac.ACHI_SO_ANCHI LIKE '%" + param.getNumber() + "%'";
        }
		if (!StringUtils.isEmpty(param.getGycbhNumber())) {
        	expression = expression +  " AND ac.POLICY_NUMBER LIKE '%" + param.getGycbhNumber() + "%'";
        }
		if (!StringUtils.isEmpty(param.getType())) {
        	expression = expression +  " AND AC.LINE_ID = :pLoaiAnchi";
        }
		
		Query query = entityManager.createNativeQuery(expression);
        query.setParameter("pType", type);
        if (!StringUtils.isEmpty(param.getType())) {
        	query.setParameter("pLoaiAnchi", param.getType());	
        }
		List<Object[]> data = query.getResultList();
        
		return convertToAnchiDTO(data);
	}
	
	private List<AnchiDTO> convertToAnchiDTO(List<Object[]> data) {
		List<AnchiDTO> result = new ArrayList<>();
		if (data == null || data.size() == 0) {
			return result;
		}
		
		for (Object[] obj : data) {
			AnchiDTO item = new AnchiDTO();
			item.setMciAddId(obj[0].toString());
			item.setAchiSoAnchi(obj[1].toString());
			item.setLineId(obj[2].toString());
			item.setAchiTenAnchi((String)obj[3]);
			item.setAchiTungay(((Date)obj[4]));
			item.setAchiPhibh(((BigDecimal)obj[5]).longValue());
			item.setAchiStienvn(((BigDecimal)obj[6]).longValue());
			item.setPolicyStatusId((String)obj[8]);
			
			result.add(item);
		}
		
		return result;
	}
}