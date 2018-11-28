package com.baoviet.agency.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.bean.AgreementNophiDTO;
import com.baoviet.agency.bean.FileContentDTO;
import com.baoviet.agency.bean.QueryResultDTO;
import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.AgreementHis;
import com.baoviet.agency.domain.Attachment;
import com.baoviet.agency.domain.Conversation;
import com.baoviet.agency.dto.CountOrderDTO;
import com.baoviet.agency.dto.OrderHistoryDTO;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.SearchAgreementVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;
import com.baoviet.agency.web.rest.vm.common.PageableVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class AgreementRepositoryImpl implements AgreementRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ConversationRepository conversationRepository;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Override
	public List<OrderHistoryDTO> getOrderHistoryByGycbhNumber(List<AgreementHis> lstAgreementHis) {
		List<OrderHistoryDTO> data = new ArrayList<>();
		
		int order = 0;
		for (AgreementHis agHis : lstAgreementHis) {
			OrderHistoryDTO orderHis = new OrderHistoryDTO();
			orderHis.setAgreementId(agHis.getAgreementId());
			orderHis.setGycbhNumber(agHis.getGycbhNumber());
			orderHis.setStatusPolicyName(agHis.getStatusPolicyName());
			orderHis.setCreateDateDisplay(DateUtils.date2Str(agHis.getCreateDate(), "HH:mm:ss dd/MM/yyyy"));
			orderHis.setHisDate(agHis.getCreateDate());
			orderHis.setType(AgencyConstants.OrderHistory.VIEW_HISTORY);
			order++;
			orderHis.setOrder(order);
			data.add(orderHis);
			List<Conversation> lstConversation = conversationRepository.findByParrentId(agHis.getAgreementId());
			if (lstConversation != null && lstConversation.size() > 0) {
				for (Conversation conver : lstConversation) {
					OrderHistoryDTO orderHisCoversation = new OrderHistoryDTO();
					List<Attachment> lstAttach = attachmentRepository.findByParrentId(conver.getConversationId());
					if (lstAttach != null && lstAttach.size() > 0) {
						List<FileContentDTO> lstFiles = new ArrayList<>();
						for (Attachment item : lstAttach) {
							FileContentDTO fileContent = new FileContentDTO();
							//fileContent.setContent(encoder.encode(item.getContent()));
							fileContent.setFilename(item.getAttachmentName());
							fileContent.setAttachmentId(item.getAttachmentId());
							lstFiles.add(fileContent);
						}
						if (lstFiles != null && lstFiles.size() > 0) {
							orderHisCoversation.setFilesContent(lstFiles);	
						}
					}
					if (conver.getCreateDate() != null) {
						orderHisCoversation.setCreateDateDisplay(DateUtils.date2Str(conver.getCreateDate(), "HH:mm:ss dd/MM/yyyy"));	
					} else {
						// TH k có thì hiển thị tạm ngày tạo đơn hàng
						orderHisCoversation.setCreateDateDisplay(DateUtils.date2Str(agHis.getCreateDate(), "HH:mm:ss dd/MM/yyyy"));	
					}
					orderHisCoversation.setType(AgencyConstants.OrderHistory.VIEW_CONVERSATION);
					orderHisCoversation.setTitle(conver.getTitle());
					orderHisCoversation.setContent(conver.getConversationContent());
					if (conver.getCreateDate() != null) {
						orderHisCoversation.setHisDate(conver.getCreateDate());	
					} else {
						// TH k có thì hiển thị tạm ngày tạo đơn hàng
						orderHisCoversation.setHisDate(agHis.getCreateDate());
					}
					if (StringUtils.isNotEmpty(conver.getUserName())) {
						orderHisCoversation.setFromEmail(conver.getUserName());
					}
					
					order++;
					orderHisCoversation.setOrder(order);
					data.add(orderHisCoversation);
				}
			}
		}
		
		
		Collections.sort(data, new Comparator<OrderHistoryDTO>() {

	        public int compare(OrderHistoryDTO o1, OrderHistoryDTO o2) {
	            // compare two AgreementHis of `Score` and return `int` as result.
	            return Long.valueOf(o2.getHisDate().getTime()).compareTo(Long.valueOf(o1.getHisDate().getTime()));
	        }
	    });
		
		if (data != null && data.size() > 0) {
			return data;
		}
		return null;
	}
	
	
	@Override
	public List<Agreement> getByGycbhNumber(String pgycbhNumber, String type) {
		String expression = "";

		if (type.equals("2")) {
			expression = "select * from agreement a where gycbh_number in(" + pgycbhNumber
					+ ") and (STATUS_POLICY_ID = '89' OR STATUS_POLICY_ID = '93' OR STATUS_POLICY_ID = '99') ";
		} else {
			expression = "select * from agreement a where gycbh_number = '" + pgycbhNumber
					+ "' and (STATUS_POLICY_ID = '89' OR STATUS_POLICY_ID = '93' OR STATUS_POLICY_ID = '99') ";
		}

		Query query = entityManager.createNativeQuery(expression, Agreement.class);

		List<Agreement> data = query.getResultList();

		if (data != null && data.size() > 0) {
			return data;
		}
		return null;
	}

	@Override
	public Agreement getAgreementByGycbhNumber(String gycbhNumber) {
		String expression = "";

		expression = "select a.*"
				+ ", c.REGISTRATION_NUMBER as REGISTRATION_NUMBER_CAR, c.BANK_ID AS CUSTOMER_CONTACT_NAME_CAR"
				+ ", m.REGISTRATION_NUMBER as REGISTRATION_NUMBER_MOTO, m.INSURED_NAME AS CUSTOMER_CONTACT_NAME_MOTO"
				+ ", t.PROPSER_NGAYSINH, t.PROPSER_NAME AS CUSTOMER_CONTACT_NAME_FLEXI"
				+ ", b.NGUOIDBH_NGAYSINH, b.NGUOIDBH_NAME AS CUSTOMER_CONTACT_NAME_BVP"
				+ ", tla.INSURED_NAME AS CUSTOMER_CONTACT_NAME_TLKHC"
				+ ", kc.INSURED_NAME AS CUSTOMER_CONTACT_NAME_KCAREKCR"
				+ ", h.INVOICE_COMPANY AS CUSTOMER_CONTACT_NAME_HOME"
				+ ", padd.INSURED_NAME AS CUSTOMER_CONTACT_NAME_PATNC"
				+ ", g.INSURED_NAME AS CUSTOMER_CONTACT_NAME_HHVCGOOD" + " from agreement a"
				+ " left join CARS c ON c.CAR_ID = a.GYCBH_ID" + " left join MOTO m ON m.ID = a.GYCBH_ID"
				+ " left join TRAVELCARE t ON t.TRAVELCARE_ID = a.GYCBH_ID" + " left join BVP b ON b.ID = a.GYCBH_ID"
				+ " left join TL tl ON tl.TL_ID = a.GYCBH_ID left join TL_ADD tla ON tla.TL_ID = tl.TL_ID"
				+ " left join KCARE kc ON kc.K_ID = a.GYCBH_ID" + " left join HOME h ON h.HOME_ID = a.GYCBH_ID"
				+ " left join PA p ON p.PA_ID = a.GYCBH_ID left join PA_ADD padd ON p.PA_ID = padd.PA_ID"
				+ " left join GOODS g ON g.HH_ID = a.GYCBH_ID" + " where a.gycbh_number =:gycbhNumber";

		Query query = entityManager.createNativeQuery(expression, Agreement.class);

		query.setParameter("gycbhNumber", gycbhNumber);

		List<Agreement> data = query.getResultList();

		if (data != null && data.size() > 0) {
			return data.get(0);
		}
		return null;
	}

	@Override
	public boolean UpdateOTP(String gycbhNunber, String otp, String otpStatus, String otpStartTime) {
		String expression = "";

		expression = "UPDATE AGREEMENT SET OTP =:otp, OTP_STATUS = :otpStatus, OTP_START_TIME = :otpStartTime WHERE POLICY_NUMBER = :gycbhNunber";

		Query query = entityManager.createNativeQuery(expression, Agreement.class);

		query.setParameter("otp", otp);
		query.setParameter("otpStatus", otpStatus);
		query.setParameter("otpStartTime", otpStartTime);
		query.setParameter("gycbhNunber", gycbhNunber);

		int result = query.executeUpdate();
		if (result > 0) {
			return true;
		}

		return false;
	}
	
	@Override
	public QueryResultDTO count(SearchAgreementVM obj, String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE AGENT_ID = :pType";

		Query query = entityManager.createNativeQuery(buildSearchReport(expression, obj, type));

		// set parameter 
		setQueryParameter(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	@Override
	public CountOrderDTO getCountAllOrder(String agentId) {
		CountOrderDTO data = new CountOrderDTO();
		SearchAgreementVM obj = new SearchAgreementVM();
		SearchAgreementWaitVM obj1 = new SearchAgreementWaitVM();
		
		QueryResultDTO countOrder = countOrder(agentId);
		QueryResultDTO countOrderLater = countOrderLater(agentId);
		QueryResultDTO countCart = countSearchCart(obj1, agentId);
		QueryResultDTO countOrderDebit = countNophi(obj, agentId);
		
		QueryResultDTO countBvWaiting = countAgreementWait(obj1, agentId, "0");
		QueryResultDTO countAgencyWaiting = countAgreementWait(obj1, agentId, "1");
		QueryResultDTO countOrderExpire = countAgreementWait(obj1, agentId, "5");
		
		data.setCountOrderMe(countOrder.getCount());
		data.setCountOrderLater(countOrderLater.getCount());
		data.setCountCart(countCart.getCount());
		data.setCountAgencyWaiting(countAgencyWaiting.getCount());
		data.setCountBvWaiting(countBvWaiting.getCount());
		data.setCountOrderExpire(countOrderExpire.getCount());
		data.setCountOrderDebit(countOrderDebit.getCount());
		return data;
	}
	
	@Override
	public CountOrderDTO getAdmCountAllOrder(String departmentId) {
		CountOrderDTO data = new CountOrderDTO();
		SearchAgreementWaitVM obj = new SearchAgreementWaitVM();

		QueryResultDTO countBvWaiting = countAdminAgreement(obj, departmentId, "0");
		QueryResultDTO countCart = countAdminAgreement(obj, departmentId, "3");
		QueryResultDTO countTrans = countAdminAgreement(obj, departmentId, "4");
		
		data.setCountBvWaiting(countBvWaiting.getCount());
		data.setCountCart(countCart.getCount());
		data.setCountOrderDebit(countTrans.getCount());
		return data;
	}
	
	@Override
	public QueryResultDTO countAdmin(SearchAgreementVM obj, String adminId) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pType ) ";
		
		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pType ) ";

		Query query = entityManager.createNativeQuery(buildSearchReport(expression, obj, adminId));

		// set parameter 
		setQueryParameter(query, obj, adminId);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	@Override
	public QueryResultDTO countNophi(SearchAgreementVM obj, String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
//		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE AGENT_ID = :pType";
		String expression = "SELECT count(p.id), NVL(sum(a.TOTAL_PREMIUM), 0) FROM AGREEMENT a JOIN AGREEMENT_NO_PHI p ON a.AGREEMENT_ID = p.AGREEMENT_ID WHERE a.CREATE_TYPE = 0 AND a.AGENT_ID = :pType";
		Query query = entityManager.createNativeQuery(buildSearchNophiExpression(expression, obj, type));

		// set parameter 
		setQueryParameter(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}

	@Override
	public Page<Agreement> search(SearchAgreementWaitVM obj, String type) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM AGREEMENT WHERE AGENT_ID = :pType";
        
        Query query = entityManager.createNativeQuery(buildSearchOrder(expression, obj, type), Agreement.class);

        // set parameter 
        setQueryParameterOrder(query, obj, type);
 		
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countOrder(obj, type);
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public QueryResultDTO countOrder(SearchAgreementWaitVM obj, String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE AGENT_ID = :pType";

		Query query = entityManager.createNativeQuery(buildSearchOrder(expression, obj, type));

		// set parameter 
		setQueryParameterOrder(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private QueryResultDTO countOrder(String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE (STATUS_POLICY_ID = '100' OR (STATUS_POLICY_ID = '91' AND PAYMENT_METHOD = 'PAYMENT_LATER')) AND AGENT_ID = :pType";

		Query query = entityManager.createNativeQuery(expression);

		// set parameter 
		query.setParameter("pType", type);		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private QueryResultDTO countOrderLater(String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE STATUS_POLICY_ID = '91' AND PAYMENT_METHOD = 'PAYMENT_LATER' AND AGENT_ID = :pType";

		Query query = entityManager.createNativeQuery(expression);

		// set parameter 
		query.setParameter("pType", type);		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	@Override
	public Page<Agreement> searchAdmin(SearchAgreementWaitVM obj, String departmentId) {
		String expression = "SELECT * FROM AGREEMENT WHERE BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
		
		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
        
        Query query = entityManager.createNativeQuery(buildSearchExpressionAgreementWait(expression, obj, "4"), Agreement.class);

        // set parameter 
        setQueryParameterAdmin(query, obj, departmentId);
 		
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAdminAgreement(obj, departmentId, "4");
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> searchCartAdmin(SearchAgreementWaitVM obj, String departmentId) {
		String expression = "SELECT * FROM AGREEMENT WHERE BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
        
		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
		
        Query query = entityManager.createNativeQuery(buildSearchExpressionAgreementWait(expression, obj, "3"), Agreement.class);

        // set parameter 
        setQueryParameterAdmin(query, obj, departmentId);
 		
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAdminAgreement(obj, departmentId, "3");
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> searchAdminBVWait(SearchAgreementWaitVM obj, String departmentId) {
		String expression = "SELECT * FROM AGREEMENT WHERE BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
		
		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
        
        Query query = entityManager.createNativeQuery(buildSearchExpressionAgreementWait(expression, obj, "0"), Agreement.class);

        // set parameter 
        setQueryParameterAdmin(query, obj, departmentId);
 		
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAdminAgreement(obj, departmentId, "0");
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> searchOrderTransport(SearchAgreementWaitVM obj, String departmentId) {
		String expression = "SELECT * FROM AGREEMENT WHERE BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
        
		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";
		
        Query query = entityManager.createNativeQuery(buildSearchExpressionAgreementWait(expression, obj, "4"), Agreement.class);

        // set parameter 
        setQueryParameterAdmin(query, obj, departmentId);
 		
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAdminAgreement(obj, departmentId, "4");
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	// // caseWait:  0: chờ bảo việt, 1: chờ đại lý, 2: Khác
	@Override
	public Page<Agreement> searchOrderWait(SearchAgreementWaitVM obj, String type, String caseWait) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM AGREEMENT WHERE AGENT_ID = :pType";
        
        Query query = entityManager.createNativeQuery(buildSearchExpressionAgreementWait(expression, obj, caseWait), Agreement.class);

        // set parameter 
 		setQueryParameterAgreementWait(query, obj, type);
 		
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAgreementWait(obj, type, caseWait);
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> searchCart(SearchAgreementWaitVM obj, String type) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM AGREEMENT WHERE STATUS_POLICY_ID ='90' AND AGENT_ID = :pType";
        
        Query query = entityManager.createNativeQuery(buildSearchCart(expression, obj, type), Agreement.class);

        // set parameter 
        setQuerySearchCart(query, obj, type);
 		
 		// Paging
 		Pageable pageable = buildPageableAgreementWait(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countSearchCart(obj, type);
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> searchCart(String type, List<String> lstStatus, Pageable pageableIn) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM AGREEMENT WHERE CREATE_TYPE IN ('0','2') AND AGENT_ID = :pType";
        
        Query query = entityManager.createNativeQuery(buildSearchCartExpression(expression, lstStatus), Agreement.class);

        // set parameter 
        query.setParameter("pType", type);
 		
        SearchAgreementVM obj = new SearchAgreementVM();
        PageableVM page = new PageableVM();
        page.setPage(pageableIn.getPageNumber());
        page.setSize(pageableIn.getPageSize());
        obj.setPageable(page);
        
 		// Paging
 		Pageable pageable = buildPageable(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countCart(obj, type);
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> getWaitAgency(String type, Pageable pageableIn) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM AGREEMENT WHERE STATUS_POLICY_ID IN ('80','81','83') AND AGENT_ID = :pType ORDER BY AGREEMENT_ID DESC";
        
        Query query = entityManager.createNativeQuery(expression, Agreement.class);

        // set parameter 
        query.setParameter("pType", type);
 		
        SearchAgreementVM obj = new SearchAgreementVM();
        PageableVM page = new PageableVM();
        page.setPage(pageableIn.getPageNumber());
        page.setSize(pageableIn.getPageSize());
        obj.setPageable(page);
        
 		// Paging
 		Pageable pageable = buildPageable(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAgreementOtp(obj, type);
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> getWaitAgencyAdmin(String type, Pageable pageableIn) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM AGREEMENT WHERE STATUS_POLICY_ID IN ('80','81','83') AND BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pType) ";
        
		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pType ) ORDER BY AGREEMENT_ID DESC";
		
        Query query = entityManager.createNativeQuery(expression, Agreement.class);

        // set parameter 
        query.setParameter("pType", type);
 		
        SearchAgreementVM obj = new SearchAgreementVM();
        PageableVM page = new PageableVM();
        page.setPage(pageableIn.getPageNumber());
        page.setSize(pageableIn.getPageSize());
        obj.setPageable(page);
         
 		// Paging
 		Pageable pageable = buildPageable(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countAgreementOtpAdmin(obj, type);
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> getWaitAgreement(String type, Pageable pageableIn) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM AGREEMENT WHERE CREATE_TYPE IN ('0','1') AND STATUS_POLICY_ID IN ('92','91','93') AND AGENT_ID = :pType ORDER BY AGREEMENT_ID DESC";
        
        Query query = entityManager.createNativeQuery(expression, Agreement.class);

        // set parameter 
        query.setParameter("pType", type);
 		
        SearchAgreementVM obj = new SearchAgreementVM();
        PageableVM page = new PageableVM();
        page.setPage(pageableIn.getPageNumber());
        page.setSize(pageableIn.getPageSize());
        obj.setPageable(page);
        
 		// Paging
 		Pageable pageable = buildPageable(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countWaitAgreement(obj, type);
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<Agreement> getWaitAgreementAdmin(String adminId, Pageable pageableIn) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT * FROM AGREEMENT WHERE CREATE_TYPE IN ('0','1') AND STATUS_POLICY_ID IN ('92','91','93') AND BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pType) ";
		
		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pType ) ORDER BY AGREEMENT_ID DESC";
        
        Query query = entityManager.createNativeQuery(expression, Agreement.class);

        // set parameter 
        query.setParameter("pType", adminId);
 		
        SearchAgreementVM obj = new SearchAgreementVM();
        PageableVM page = new PageableVM();
        page.setPage(pageableIn.getPageNumber());
        page.setSize(pageableIn.getPageSize());
        obj.setPageable(page);
        
 		// Paging
 		Pageable pageable = buildPageable(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        
        List<Agreement> data = query.getResultList();
        QueryResultDTO count = countWaitAgreementAdmin(obj, adminId);
        
        // Build pageable
        Page<Agreement> dataPage = new PageImpl<>(data, pageable, count.getCount());
        
        return dataPage;
	}
	
	@Override
	public Page<AgreementNophiDTO> searchNophi(SearchAgreementVM obj, String type) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT p.ID, a.GYCBH_NUMBER, a.CONTACT_NAME, a.LINE_ID, a.LINE_NAME, a.CONTACT_PHONE, a.STATUS_POLICY_NAME, a.INCEPTION_DATE, a.EXPIRED_DATE, a.TOTAL_PREMIUM, p.SOTIEN, p.AGREEMENT_ID, p.CONTACT_ID, p.NOTE FROM AGREEMENT a JOIN AGREEMENT_NO_PHI p ON a.AGREEMENT_ID = p.AGREEMENT_ID WHERE a.CREATE_TYPE = 0 AND a.AGENT_ID = :pType";
        
        Query query = entityManager.createNativeQuery(buildSearchNophiExpression(expression, obj, type));

        // Paging
 		Pageable pageable = buildPageable(obj);
 		if (pageable != null) {
 			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize()); 
 			query.setMaxResults(pageable.getPageSize());
 		}
        // set parameter 
 		setQueryParameter(query, obj, type);
 		
 		List<Object[]> result = query.getResultList();
 		if (result != null) {
 			List<AgreementNophiDTO> data = convertResultToAgreementNophi(result);
 	        
 	        QueryResultDTO count = countNophi(obj, type);
 	        
 	        // Build pageable
 	        Page<AgreementNophiDTO> dataPage = new PageImpl<>(data, pageable, count.getCount());
 	        
 	        return dataPage;
 		}
 		return null;
	}
	
	private String buildSearchReport(String expression, SearchAgreementVM obj, String type) {
        if (obj.getLstStatusPolicy() != null && obj.getLstStatusPolicy().size() > 0) {
        	if (!StringUtils.isEmpty(StringUtils.join(obj.getLstStatusPolicy(), "','"))) {
        		String tempId = "'" + StringUtils.join(obj.getLstStatusPolicy(), "','") + "'";
            	expression = expression +  " AND STATUS_POLICY_ID IN (" + tempId + ")";//  :pStatusPolicy)";
        	} else {
        		expression = expression +  " AND STATUS_POLICY_ID IN ('91','92','100')";//  đơn hàng đại ly
        	}
        }
        if (obj.getFromDate() != null) {
        	expression = expression +  " AND TRUNC(AGREEMENT_SYSDATE) >= TRUNC(:pFromDate)";
        } 
        if (obj.getToDate() != null) {
        	expression = expression +  " AND TRUNC(AGREEMENT_SYSDATE) <= TRUNC(:pToDate)";
        }
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	expression = expression +  " AND BAOVIET_DEPARTMENT_ID = :pDepartmentId";
        }
        
        // ORDER
        expression = expression +  " ORDER BY AGREEMENT_ID DESC";
        
        return expression;
	}
	
	private String buildSearchExpression(String expression, SearchAgreementVM obj, String type) {
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
        if (obj.getLstStatusPolicy() != null && obj.getLstStatusPolicy().size() > 0) {
        	if (!StringUtils.isEmpty(StringUtils.join(obj.getLstStatusPolicy(), "','"))) {
        		String tempId = "'" + StringUtils.join(obj.getLstStatusPolicy(), "','") + "'";
            	expression = expression +  " AND STATUS_POLICY_ID IN (" + tempId + ")";//  :pStatusPolicy)";
        	} else {
        		expression = expression +  " AND STATUS_POLICY_ID IN ('91','92','100')";//  đơn hàng đại ly
        	}
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
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	expression = expression +  " AND BAOVIET_DEPARTMENT_ID = :pDepartmentId";
        }
        if (!StringUtils.isEmpty(obj.getCreateDate())) {
        	expression = expression +  " AND TRUNC(AGREEMENT_SYSDATE) = TRUNC(To_date('"+obj.getCreateDate()+"', 'dd/mm/yyyy'))";
        }
        
        // ORDER
        expression = expression +  " ORDER BY AGREEMENT_ID DESC";
        
        return expression;
	}
	
	// caseWait:  0: chờ bảo việt, 1: chờ đại lý, 2: Khác; 5 quá hạn
	private String buildSearchExpressionAgreementWait(String expression, SearchAgreementWaitVM obj, String caseWait) {
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
        
        // chờ đại lý giải quyết
        if (StringUtils.equals(caseWait, "1")) {
        	if (!StringUtils.isEmpty(obj.getStatusPolicy())) {
            	expression = expression +  " AND STATUS_POLICY_ID ='" + obj.getStatusPolicy() + "'";
        	} else {
        		expression = expression +  " AND STATUS_POLICY_ID IN ('80','81','83')";
        	}
        } else if (StringUtils.equals(caseWait, "0")) {	// chờ bảo việt giải quyết
        	if (!StringUtils.isEmpty(obj.getStatusPolicy())) {
        		if (obj.getStatusPolicy().equals("91")) {
        			expression = expression +  " AND STATUS_POLICY_ID = '91' AND PAYMENT_METHOD != 'PAYMENT_LATER'";
        		} else {
        			expression = expression +  " AND STATUS_POLICY_ID ='" + obj.getStatusPolicy() + "'";	
        		}
            	
        	} else {
            		expression = expression +  " AND STATUS_POLICY_ID IN ('91','92','93') AND PAYMENT_METHOD != 'PAYMENT_LATER' ";
            }
        } else if (StringUtils.equals(caseWait, "2")) { // yêu cầu bảo hiểm khác
        	if (!StringUtils.isEmpty(obj.getStatusPolicy())) {
       			expression = expression +  " AND STATUS_POLICY_ID ='" + obj.getStatusPolicy() + "'";	
        	} else {
        		expression = expression +  " AND STATUS_POLICY_ID IN ('89','99','84')";
        	}
        } else if (StringUtils.equals(caseWait, "3")) { // admin màn hình giỏ hàng bảo việt
        	if (!StringUtils.isEmpty(obj.getStatusPolicy())) {
       			expression = expression +  " AND STATUS_POLICY_ID ='" + obj.getStatusPolicy() + "'";	
        	} else {
        		expression = expression +  " AND STATUS_POLICY_ID = '90'";
        	}
        } else if (StringUtils.equals(caseWait, "4")) { // admin màn hình đơn hàng bảo việt (cả màn hình vận đơn)
        	if (!StringUtils.isEmpty(obj.getStatusPolicy())) {
       			expression = expression +  " AND STATUS_POLICY_ID ='" + obj.getStatusPolicy() + "'";	
        	} else {
        		expression = expression +  " AND STATUS_POLICY_ID IN ('91','92','100')";
        	}
        } else if (StringUtils.equals(caseWait, "5")) { // agency màn hình đơn hàng quá hạn
       			expression = expression +  " AND STATUS_POLICY_ID = '84' ";	
        } else {
        	
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
	
	private String buildSearchCart(String expression, SearchAgreementWaitVM obj, String type) {
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
        if (obj.getFromDate() != null) {
        	expression = expression +  " AND TRUNC(INCEPTION_DATE) >= TRUNC(:pFromDate)";
        } 
        if (obj.getToDate() != null) {
        	expression = expression +  " AND TRUNC(EXPIRED_DATE) <= TRUNC(:pToDate)";
        }
        if (!StringUtils.isEmpty(obj.getCreateType())) {
        	expression = expression +  " AND CREATE_TYPE = :pCreateType";
        }
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	expression = expression +  " AND BAOVIET_DEPARTMENT_ID = :pDepartment";
        }
        if (!StringUtils.isEmpty(obj.getCreateDate())) {
        	expression = expression +  " AND TRUNC(AGREEMENT_SYSDATE) = TRUNC(To_date('"+obj.getCreateDate()+"', 'dd/mm/yyyy'))";
        }
        
        // ORDER
        expression = expression +  " ORDER BY AGREEMENT_ID DESC";
        
        return expression;
	}
	
	private String buildSearchCartExpression(String expression, List<String> lstStatus) {
        if (lstStatus != null && lstStatus.size() > 0) {
        	if (!StringUtils.isEmpty(StringUtils.join(lstStatus, "','"))) {
        		String tempId = "'" + StringUtils.join(lstStatus, "','") + "'";
            	expression = expression +  " AND STATUS_POLICY_ID IN (" + tempId + ")";//  :pStatusPolicy)";
        	}
        }
        
        // ORDER
        expression = expression +  " ORDER BY AGREEMENT_ID DESC";
        
        return expression;
	}
	
	private String buildSearchNophiExpression(String expression, SearchAgreementVM obj, String type) {
		if (!StringUtils.isEmpty(obj.getContactName())) {
        	expression = expression +  " AND UPPER(a.CONTACT_NAME) LIKE '%" + obj.getContactName().toUpperCase() + "%'";
        }
        if (!StringUtils.isEmpty(obj.getEmail())) {
        	expression = expression +  " AND UPPER(CONTACT_USERNAME) LIKE '%" + obj.getEmail().toUpperCase() + "%'";
        }
        if (!StringUtils.isEmpty(obj.getGycbhNumber())) {
        	expression = expression +  " AND UPPER(GYCBH_NUMBER) LIKE '%" + obj.getGycbhNumber().toUpperCase() + "%'";
        }
        if (!StringUtils.isEmpty(obj.getPhone())) {
        	expression = expression +  " AND a.CONTACT_PHONE LIKE '%" + obj.getPhone() + "%'";
        } 
        if (!StringUtils.isEmpty(obj.getProductCode())) {
        	expression = expression +  " AND a.LINE_ID = :pLineId";
        } 
        if (obj.getLstStatusPolicy() != null && obj.getLstStatusPolicy().size() > 0) {
        	if (!StringUtils.isEmpty(StringUtils.join(obj.getLstStatusPolicy(), "','"))) {
        		String tempId = "'" + StringUtils.join(obj.getLstStatusPolicy(), "','") + "'";
            	expression = expression +  " AND a.STATUS_POLICY_ID IN (" + tempId + ")";//  :pStatusPolicy)";
        	}
        }
        if (obj.getFromDate() != null) {
        	expression = expression +  " AND TRUNC(INCEPTION_DATE) >= TRUNC(:pFromDate)";
        } 
        if (obj.getToDate() != null) {
        	expression = expression +  " AND TRUNC(EXPIRED_DATE) <= TRUNC(:pToDate)";
        }
        
        // ORDER
        expression = expression +  " ORDER BY a.AGREEMENT_ID DESC";
        
        return expression;
	}
	
	private List<AgreementNophiDTO> convertResultToAgreementNophi(List<Object[]> result) {
		List<AgreementNophiDTO> data = new ArrayList<AgreementNophiDTO>();
 		for (Object[] obj : result) {
 			AgreementNophiDTO item = new AgreementNophiDTO();
 			item.setId((String)obj[0]);
 			item.setGycbhNumber((String)obj[1]);
 			item.setContactName((String)obj[2]);
 			item.setLineId((String)obj[3]);
 			item.setLineName((String)obj[4]);
 			item.setContactPhone((String)obj[5]);
 			item.setStatusPolicyName((String)obj[6]);
 			Date inceptionDate = new Date();
 			inceptionDate = (Date) obj[7];
 			item.setInceptionDate(DateUtils.date2Str(inceptionDate));
 			Date expiredDate = new Date();
 			expiredDate = (Date) obj[8];
 			item.setExpiredDate(DateUtils.date2Str(expiredDate));
 			item.setTotalPremium((BigDecimal) obj[9]);
 			item.setSotien((BigDecimal) obj[10]);
 			item.setAgreementId((String) obj[11]);
 			item.setContactId((String) obj[12]);
 			item.setNote((String) obj[13]);
 			data.add(item);
		}
 		
 		return data;
	}

	private QueryResultDTO countCart(SearchAgreementVM obj, String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE CREATE_TYPE IN ('0','2') AND STATUS_POLICY_ID IN ('90','92') AND AGENT_ID = :pType";

		Query query = entityManager.createNativeQuery(buildSearchExpression(expression, obj, type));

		// set parameter 
		setQueryParameter(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private QueryResultDTO countAgreementOtp(SearchAgreementVM obj, String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE STATUS_POLICY_ID IN ('80','81','83') AND AGENT_ID = :pType";

		Query query = entityManager.createNativeQuery(buildSearchExpression(expression, obj, type));

		// set parameter 
		setQueryParameter(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private QueryResultDTO countAgreementOtpAdmin(SearchAgreementVM obj, String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE CREATE_TYPE = '0' AND STATUS_POLICY_ID IN ('80','81','83') AND BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pType) ";

		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pType )";
		
		Query query = entityManager.createNativeQuery(buildSearchExpression(expression, obj, type));

		// set parameter 
		setQueryParameter(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private QueryResultDTO countWaitAgreement(SearchAgreementVM obj, String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE CREATE_TYPE IN ('0','1') AND STATUS_POLICY_ID IN ('92','91','93') AND AGENT_ID = :pType";

		Query query = entityManager.createNativeQuery(buildSearchExpression(expression, obj, type));

		// set parameter 
		setQueryParameter(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private QueryResultDTO countWaitAgreementAdmin(SearchAgreementVM obj, String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE CREATE_TYPE IN ('0','1') AND STATUS_POLICY_ID IN ('92','91','93') AND BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pType) ";

		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pType )";
		
		Query query = entityManager.createNativeQuery(buildSearchExpression(expression, obj, type));

		// set parameter 
		setQueryParameter(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private void setQueryParameter(Query query, SearchAgreementVM obj, String type) {
		query.setParameter("pType", type);
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
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	query.setParameter("pDepartmentId", obj.getDepartmentId());
        }
	}
	
	private void setQueryParameterOrder(Query query, SearchAgreementWaitVM obj, String type) {
		query.setParameter("pType", type);
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
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	query.setParameter("pDepartmentId", obj.getDepartmentId());
        }
	}
	
	private void setQueryParameterAdmin(Query query, SearchAgreementWaitVM obj, String departmentId) {
		query.setParameter("pDepartmentId", departmentId);
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
        
	}
	
	private Pageable buildPageable(SearchAgreementVM obj) {
		Pageable pageable = null;
		if (obj.getPageable() == null) {
			return pageable;
		}
		
		String[] sorts = obj.getPageable().getSort().split(",");
		Sort sort = new Sort(Direction.fromString(sorts[1]), sorts[0]);
		pageable = new PageRequest(obj.getPageable().getPage(), obj.getPageable().getSize(), sort);
		return pageable;
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
	
	private void setQuerySearchCart(Query query, SearchAgreementWaitVM obj, String type) {
		query.setParameter("pType", type);
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
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	query.setParameter("pDepartment", obj.getDepartmentId());
        }
	}
	
	private void setQueryParameterAgreementWait(Query query, SearchAgreementWaitVM obj, String type) {
		query.setParameter("pType", type);
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
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	query.setParameter("pDepartment", obj.getDepartmentId());
        }
	}
	
	private QueryResultDTO countAgreementWait(SearchAgreementWaitVM obj, String type, String caseWait) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE AGENT_ID = :pType";

		Query query = entityManager.createNativeQuery(buildSearchExpressionAgreementWait(expression, obj, caseWait));

		// set parameter 
		setQueryParameterAgreementWait(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private QueryResultDTO countAdminAgreement(SearchAgreementWaitVM obj, String departmentId, String caseWait) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pDepartmentId )";
		
		expression = expression +  " AND LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pDepartmentId ) ";

		Query query = entityManager.createNativeQuery(buildSearchExpressionAgreementWait(expression, obj, caseWait));

		// set parameter 
		setQueryParameterAdmin(query, obj, departmentId);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private QueryResultDTO countSearchCart(SearchAgreementWaitVM obj, String type) {
		QueryResultDTO result = new QueryResultDTO();
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "SELECT count(*), NVL(sum(TOTAL_PREMIUM), 0) FROM AGREEMENT WHERE STATUS_POLICY_ID ='90' AND AGENT_ID = :pType";

		Query query = entityManager.createNativeQuery(buildSearchCart(expression, obj, type));

		// set parameter 
		setQuerySearchCart(query, obj, type);
		
        Object[] data = (Object[]) query.getSingleResult();
        
        if (data != null) {
        	BigDecimal tempCount = (BigDecimal) data[0];
        	BigDecimal tempPremium = (BigDecimal) data[1];
        	result.setCount(tempCount.longValue());
        	result.setPremium(tempPremium.longValue());
        }
        
        return result;
	}
	
	private String buildSearchOrder(String expression, SearchAgreementWaitVM obj, String caseType) {
		if (!StringUtils.isEmpty(obj.getStatusPolicy())) {
			if (obj.getStatusPolicy().equals("91")) {
				expression = expression +  " AND STATUS_POLICY_ID = '91' AND PAYMENT_METHOD = 'PAYMENT_LATER' ";
			} else {
				expression = expression +  " AND STATUS_POLICY_ID = '" + obj.getStatusPolicy() + "'";	
			}
    	} else {
    		expression = expression +  " AND (STATUS_POLICY_ID = '100' OR (STATUS_POLICY_ID = '91' AND PAYMENT_METHOD = 'PAYMENT_LATER'))";
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
        if (obj.getFromDate() != null) {
        	expression = expression +  " AND TRUNC(INCEPTION_DATE) >= TRUNC(:pFromDate)";
        } 
        if (obj.getToDate() != null) {
        	expression = expression +  " AND TRUNC(EXPIRED_DATE) <= TRUNC(:pToDate)";
        }
        if (!StringUtils.isEmpty(obj.getCreateType())) {
        	expression = expression +  " AND CREATE_TYPE = :pCreateType";
        }
        if (!StringUtils.isEmpty(obj.getDepartmentId())) {
        	expression = expression +  " AND BAOVIET_DEPARTMENT_ID = :pDepartmentId";
        }
        if (!StringUtils.isEmpty(obj.getCreateDate())) {
        	expression = expression +  " AND TRUNC(AGREEMENT_SYSDATE) = TRUNC(To_date('"+obj.getCreateDate()+"', 'dd/mm/yyyy'))";
        }
        
        // ORDER
        expression = expression +  " ORDER BY AGREEMENT_ID DESC";
        
        return expression;
	}
	
}