package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.bean.AgreementNophiDTO;
import com.baoviet.agency.bean.QueryResultDTO;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.AgreementHis;
import com.baoviet.agency.dto.CountOrderDTO;
import com.baoviet.agency.dto.OrderHistoryDTO;
import com.baoviet.agency.web.rest.vm.SearchAgreementVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface AgreementRepositoryExtend {

	List<Agreement> getByGycbhNumber(String gycbhNumber, String type);

	Agreement getAgreementByGycbhNumber(String gycbhNumber);
	
	boolean UpdateOTP(String gycbhNunber, String otp, String otpStatus, String otpStartTime);
	
	Page<Agreement> search(SearchAgreementVM param, String type);
	
	Page<Agreement> searchOrderWait(SearchAgreementWaitVM param, String type, String caseWait);	// caseWait: 1: chờ đại lý, 0: chờ bảo việt 
	
	Page<AgreementNophiDTO> searchNophi(SearchAgreementVM param, String type);
	
	QueryResultDTO count(SearchAgreementVM param, String type);
	
	QueryResultDTO countOrder(SearchAgreementVM param, String type);
	
	QueryResultDTO countAdmin(SearchAgreementVM param, String type);
	
	QueryResultDTO countNophi(SearchAgreementVM param, String type);
	
	Page<Agreement> searchCart(String type, List<String> lstStatus, Pageable pageable);
	
	Page<Agreement> getWaitAgency(String type, Pageable pageable);
	
	Page<Agreement> getWaitAgreement(String type, Pageable pageable);
	
	Page<Agreement> searchCart(SearchAgreementWaitVM param, String type);
	
	List<OrderHistoryDTO> getOrderHistoryByGycbhNumber(List<AgreementHis> lstAgreementHis);
	
	Page<Agreement> searchAdmin(SearchAgreementWaitVM param, String departmentId);
	
	Page<Agreement> searchCartAdmin(SearchAgreementWaitVM param, String departmentId);
	
	Page<Agreement> searchAdminBVWait(SearchAgreementWaitVM param, String departmentId);
	
	Page<Agreement> searchOrderTransport(SearchAgreementWaitVM param, String departmentId);
	
	Page<Agreement> getWaitAgreementAdmin(String adminId, Pageable pageable);
	
	Page<Agreement> getWaitAgencyAdmin(String type, Pageable pageable);
	
	CountOrderDTO getCountAllOrder(String agentId);
}