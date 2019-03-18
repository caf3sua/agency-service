package com.baoviet.agency.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.bean.QueryResultDTO;
import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.MvClaOutletLocation;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.CountOrderDTO;
import com.baoviet.agency.dto.DepartmentDTO;
import com.baoviet.agency.service.impl.AgentReminderServiceImpl;
import com.baoviet.agency.web.rest.vm.AdminSearchAgencyVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class AdminUserRepositoryImpl implements AdminUserRepositoryExtend {

	private final Logger log = LoggerFactory.getLogger(AgentReminderServiceImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private MvClaOutletLocationRepository mvClaOutletLocationRepository;

	@Override
	public CountOrderDTO getAdmCountAllOrder(String departmentId) {
		
		List<MvClaOutletLocation> lstMvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsId(departmentId);
		
		if (lstMvClaOutletLocation!= null && lstMvClaOutletLocation.size() > 0) {
			CountOrderDTO data = new CountOrderDTO();
			SearchAgreementWaitVM obj = new SearchAgreementWaitVM();
			SearchAgreementWaitVM objLater = new SearchAgreementWaitVM();
			objLater.setStatusPolicy(AgencyConstants.AgreementStatus.THANH_TOAN_SAU);

			QueryResultDTO countBvWaiting = countAdminAgreement(obj, departmentId, "0", lstMvClaOutletLocation.get(0).getOutletTypeCode());
			QueryResultDTO countCart = countAdminAgreement(obj, departmentId, "3", lstMvClaOutletLocation.get(0).getOutletTypeCode());
			QueryResultDTO countTrans = countAdminAgreement(obj, departmentId, "4", lstMvClaOutletLocation.get(0).getOutletTypeCode());
			QueryResultDTO countOrderLater = countAdminAgreement(objLater, departmentId, "4", lstMvClaOutletLocation.get(0).getOutletTypeCode());
			
			data.setCountBvWaiting(countBvWaiting.getCount());
			data.setCountCart(countCart.getCount());
			data.setCountOrderDebit(countTrans.getCount());
			data.setCountOrderLater(countOrderLater.getCount());
			return data;
		}
		 return null;
	}
	
	@Override
	public Page<Agreement> searchCartAdmin(SearchAgreementWaitVM obj, String idLogin) {
		String expression = "SELECT * FROM AGREEMENT WHERE 1 = 1";
		
		List<MvClaOutletLocation> lstMvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsId(idLogin);
		
		if (lstMvClaOutletLocation != null && lstMvClaOutletLocation.size() > 0) {
			if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("VPDD")) {
				expression += " AND BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
				expression += " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
			}
			
			if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("CTTV")) {
				expression += " AND BAOVIET_COMPANY_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
				expression += " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
			}
		}
		
        Query query = entityManager.createNativeQuery(buildSearchExpression(expression, obj, "3"), Agreement.class);

        // set parameter 
        setQueryParameterAdmin(query, obj, idLogin, lstMvClaOutletLocation.get(0).getOutletTypeCode());
 		
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAdminAgreement(obj, idLogin, "3", lstMvClaOutletLocation.get(0).getOutletTypeCode());
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> searchAdminBVWait(SearchAgreementWaitVM obj, String idLogin) {
		String expression = "SELECT * FROM AGREEMENT WHERE 1 = 1";
		
		List<MvClaOutletLocation> lstMvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsId(idLogin);
		
		if (lstMvClaOutletLocation != null && lstMvClaOutletLocation.size() > 0) {
			if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("VPDD")) {
				expression += " AND BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
				expression += " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
			}
			
			if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("CTTV")) {
				expression += " AND BAOVIET_COMPANY_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
				expression += " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
			}
		}
        
        Query query = entityManager.createNativeQuery(buildSearchExpression(expression, obj, "0"), Agreement.class);

        // set parameter 
        setQueryParameterAdmin(query, obj, idLogin, lstMvClaOutletLocation.get(0).getOutletTypeCode());
 		
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAdminAgreement(obj, idLogin, "0", lstMvClaOutletLocation.get(0).getOutletTypeCode());
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> searchOrderTransport(SearchAgreementWaitVM obj, String idLogin) {
		String expression = "SELECT * FROM AGREEMENT WHERE 1 = 1";
		
		List<MvClaOutletLocation> lstMvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsId(idLogin);
		
		if (lstMvClaOutletLocation != null && lstMvClaOutletLocation.size() > 0) {
			if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("VPDD")) {
				expression += " AND BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
				expression += " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
			}
			
			if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("CTTV")) {
				expression += " AND BAOVIET_COMPANY_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
				expression += " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
			}
		}
		
		Query query = entityManager.createNativeQuery(buildSearchExpression(expression, obj, "4"), Agreement.class);

        // set parameter 
		setQueryParameterAdmin(query, obj, idLogin, lstMvClaOutletLocation.get(0).getOutletTypeCode());
        
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAdminAgreement(obj, idLogin, "4", lstMvClaOutletLocation.get(0).getOutletTypeCode());
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	
	
	
	private QueryResultDTO countAdminAgreement(SearchAgreementWaitVM obj, String departmentId, String caseWait, String typeAdmin) {
		QueryResultDTO result = new QueryResultDTO();
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE 1 = 1";
		
		if (StringUtils.isNotEmpty(typeAdmin)) {
			if (typeAdmin.equals("VPDD")) {
				expression += " AND BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
				expression += " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
			}
			
			if (typeAdmin.equals("CTTV")) {
				expression += " AND BAOVIET_COMPANY_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
				expression += " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
			}
		}
		
		Query query = entityManager.createNativeQuery(buildSearchExpression(expression, obj, caseWait));

		// set parameter 
		setQueryParameterAdmin(query, obj, departmentId, typeAdmin);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private Pageable buildPageableAgreementWait(SearchAgreementWaitVM obj) {
		Pageable pageable = null;
		if (obj.getPageable() == null) {
			return pageable;
		}
		
		String[] sorts = obj.getPageable().getSort().split(",");
		Sort sort = new Sort(Direction.fromString(sorts[1]), sorts[0]);
		pageable = new PageRequest(obj.getPageable().getPage(), obj.getPageable().getSize(), sort);
		return pageable;
	}
	
	private void setQueryParameterAdmin(Query query, SearchAgreementWaitVM obj, String departmentId, String typeAdmin) {
		if (StringUtils.equals(typeAdmin, "HOFF")) {
			
		} else {
			query.setParameter("pDepartmentId", departmentId);	
		}
        
		if (!StringUtils.isEmpty(obj.getProductCode())) {
        	query.setParameter("pLineId", obj.getProductCode());
        } 
        // Date
        if (obj.getFromDate() != null) {
        	query.setParameter("pFromDate", obj.getFromDate());
        } 
        if (obj.getToDate() != null) {
        	query.setParameter("pToDate", obj.getToDate());
        }
        if (!StringUtils.isEmpty(obj.getCreateType())) {
        	query.setParameter("pCreateType", obj.getCreateType());
        }
        if (!StringUtils.isEmpty(obj.getAgentId())) {
        	query.setParameter("pAgentId", obj.getAgentId());
        }
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	query.setParameter("pDepartment", obj.getDepartmentId());
        }
        if (!StringUtils.isEmpty(obj.getCompanyId())) {
        	query.setParameter("pCompany", obj.getCompanyId());
        }
	}
	
	private String buildSearchExpression(String expression, SearchAgreementWaitVM obj, String caseWait) {
		if (StringUtils.equals(caseWait, "3")) { // admin màn hình giỏ hàng bảo việt
        	if (!StringUtils.isEmpty(obj.getStatusPolicy())) {
       			expression = expression +  " AND STATUS_POLICY_ID ='" + obj.getStatusPolicy() + "'";	
        	} else {
        		expression = expression +  " AND STATUS_POLICY_ID = '90'";
        	}
        } else if (StringUtils.equals(caseWait, "0")) {	// chờ bảo việt giải quyết
        	if (!StringUtils.isEmpty(obj.getStatusPolicy())) {
        		if (obj.getStatusPolicy().equals("91")) {
        			expression = expression +  " AND STATUS_POLICY_ID = '91' AND (PAYMENT_METHOD != 'PAYMENT_LATER' OR PAYMENT_METHOD IS NULL)";
        		} else {
        			expression = expression +  " AND STATUS_POLICY_ID ='" + obj.getStatusPolicy() + "'";	
        		}
        	} else {
            		expression = expression +  " AND (STATUS_POLICY_ID = '93' OR ( STATUS_POLICY_ID = '91' AND (PAYMENT_METHOD != 'PAYMENT_LATER' OR PAYMENT_METHOD IS NULL) ))";
            }
        } else if (StringUtils.equals(caseWait, "4")) { // admin màn hình đơn hàng bảo việt (cả màn hình vận đơn)
        	if (!StringUtils.isEmpty(obj.getStatusPolicy())) {
        		if (obj.getStatusPolicy().equals(AgencyConstants.AgreementStatus.THANH_TOAN_SAU)) {
        			expression = expression +  " AND STATUS_POLICY_ID = '91' AND PAYMENT_METHOD = 'PAYMENT_LATER'";
        		} else if (obj.getStatusPolicy().equals(AgencyConstants.AgreementStatus.DA_THANH_TOAN)) {
        			expression = expression +  " AND STATUS_POLICY_ID = '91' AND (PAYMENT_METHOD != 'PAYMENT_LATER' OR PAYMENT_METHOD IS NULL) ";
        		} else {
        			expression = expression +  " AND STATUS_POLICY_ID ='" + obj.getStatusPolicy() + "'";        			
        		}
        	} else {
        		expression = expression +  " AND STATUS_POLICY_ID IN ('91', '100')";
        	}
        } else {
        	
        }
		
		if (!StringUtils.isEmpty(obj.getContactName())) {
        	expression = expression +  " AND UPPER(CONTACT_NAME) LIKE '%" + obj.getContactName().toUpperCase() + "%'";
        }
        if (!StringUtils.isEmpty(obj.getEmail())) {
        	expression = expression +  " AND UPPER(CONTACT_USERNAME) LIKE '%" + obj.getEmail().toUpperCase() + "%'";
        }
        if (!StringUtils.isEmpty(obj.getGycbhNumber())) {
        	expression = expression +  " AND UPPER(GYCBH_NUMBER) LIKE '%" + obj.getGycbhNumber().toUpperCase() + "%'";
        }
        if (!StringUtils.isEmpty(obj.getPhone())) {
        	expression = expression +  " AND CONTACT_PHONE LIKE '%" + obj.getPhone() + "%'";
        } 
        if (!StringUtils.isEmpty(obj.getProductCode())) {
        	expression = expression +  " AND LINE_ID = :pLineId";
        }
        
        if (!StringUtils.isEmpty(obj.getAgentId())) {
        	expression = expression +  " AND AGENT_ID = :pAgentId";
        }
        
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	expression = expression +  " AND BAOVIET_DEPARTMENT_ID = :pDepartment";
        }
        
        if (!StringUtils.isEmpty(obj.getCompanyId())) {
        	expression = expression +  " AND BAOVIET_COMPANY_ID = :pCompany";
        }
        
        if (obj.getFromDate() != null) {
        	expression = expression +  " AND TRUNC(INCEPTION_DATE) >= TRUNC(:pFromDate)";
        } 
        if (obj.getToDate() != null) {
        	expression = expression +  " AND TRUNC(EXPIRED_DATE) <= TRUNC(:pToDate)";
        }
        if (!StringUtils.isEmpty(obj.getCreateType())) {
        	expression = expression +  " AND CREATE_TYPE = :pCreateType";
        }
        if (!StringUtils.isEmpty(obj.getCreateDate())) {
        	expression = expression +  " AND TRUNC(AGREEMENT_SYSDATE) = TRUNC(To_date('"+obj.getCreateDate()+"', 'dd/mm/yyyy'))";
        }
        
        // ORDER
        expression = expression +  " ORDER BY AGREEMENT_ID DESC";
        
        return expression;
	}
	
	
	@Override
	public List<DepartmentDTO> searchDepartmentByPr(AdminSearchAgencyVM param, String idLogin) {
		
		List<MvClaOutletLocation> lstMvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsId(param.getDepartmentId());
		if (lstMvClaOutletLocation != null && lstMvClaOutletLocation.size() > 0) {
			String expression = "";
			if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("VPDD")) {
				expression = "SELECT OL.OUTLET_AMS_ID departmentId, OL.OUTLET_NAME departmentName FROM CLA_OUTLET_LOCATION OL WHERE OL.PR_OUTLET_AMS_ID = '"+ param.getDepartmentId() +"' AND OL.OUTLET_AMS_ID = '"+ idLogin +"' AND OL.OUTLET_TYPE_CODE = 'VPDD' AND OL.OUTLET_AMS_ID IS NOT NULL";
			}
			
			if (lstMvClaOutletLocation.get(0).getOutletTypeCode().equals("CTTV")) {
				expression = "SELECT OL.OUTLET_AMS_ID departmentId, OL.OUTLET_NAME departmentName FROM CLA_OUTLET_LOCATION OL WHERE OL.PR_OUTLET_AMS_ID = '"+ param.getDepartmentId() +"' AND OL.OUTLET_TYPE_CODE = 'VPDD' AND OL.OUTLET_AMS_ID IS NOT NULL";
			}
			
	        Query query = entityManager.createNativeQuery(expression);
	        
	        List<Object[]> data = query.getResultList();
	        log.debug("Request to searchAgency : data {} ", data.size());
	        if (data != null && data.size() > 0) {
	        	return convertDepartmentToDTO(data);
	        }
		}
		
        return null;
	}
	
	@Override
	public List<AgencyDTO> searchAgency(AdminSearchAgencyVM param, String adminId) {
		log.debug("Request to searchAgency : AdminSearchAgencyVM {} ", param);

		String expression1 = "SELECT OL.OUTLET_AMS_ID ma, OL.OUTLET_NAME ten FROM CLA_OUTLET_LOCATION OL WHERE OL.PR_OUTLET_AMS_ID = '"+ param.getDepartmentId() +"' AND OL.OUTLET_AMS_ID IS NOT NULL";
		
		String expression2 = "SELECT AA.AGENT_CODE ma, AA.AGENT_NAME ten FROM AGENT_AGREEMENT AA WHERE AA.DEPARTMENT_CODE = '"+ param.getDepartmentId() +"' AND AA.AGENT_CODE IS NOT NULL";
		
//        if (!StringUtils.isEmpty(param.getDepartmentId())) {
//        	expression1 = expression1 +  " AND OL.PR_OUTLET_AMS_ID = '"+ param.getDepartmentId() +"' ";
//        	expression2 = expression2 +  " AND AA.DEPARTMENT_CODE = '"+ param.getDepartmentId() +"' ";
//        } 
        
        String expression = expression1 + " UNION " + expression2;
		
        Query query = entityManager.createNativeQuery(expression);
        
        List<Object[]> data = query.getResultList();
        log.debug("Request to searchAgency : data {} ", data.size());
        if (data != null && data.size() > 0) {
        	return convertToDTO(data, param);
        }
        return null;
	}
	
	@Override
	public List<DepartmentDTO> searchDepartment(String agentId) {
		String expression1 = "SELECT OL.PR_OUTLET_AMS_ID departmentId, OL.PR_OUTLET_NAME departmentName FROM CLA_OUTLET_LOCATION OL WHERE OL.OUTLET_AMS_ID = '"+ agentId +"' AND OL.PR_OUTLET_AMS_ID IS NOT NULL";
		
		String expression2 = "SELECT AA.DEPARTMENT_CODE departmentId, AA.DEPARTMENT_NAME departmentName FROM AGENT_AGREEMENT AA WHERE AA.AGENT_CODE  = '"+ agentId +"' AND AA.DEPARTMENT_CODE IS NOT NULL";
		
        String expression = expression1 + " UNION " + expression2;
		
        Query query = entityManager.createNativeQuery(expression);
        
        List<Object[]> data = query.getResultList();
        log.debug("Request to searchAgency : data {} ", data.size());
        if (data != null && data.size() > 0) {
        	return convertDepartmentToDTO(data);
        }
        return null;
	}
	
	private List<DepartmentDTO> convertDepartmentToDTO(List<Object[]> data) {
		List<DepartmentDTO> lstResult = new ArrayList<>();
		for (Object[] item : data) {
			DepartmentDTO department = new DepartmentDTO();
			department.setDepartmentId(item[0].toString());
			department.setDepartmentName(item[1].toString());
			lstResult.add(department);
		}
		
		return lstResult;
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