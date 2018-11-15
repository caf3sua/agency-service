package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Agreement;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, String>, AgreementRepositoryExtend{
	
	Page<Agreement> findByContactIdAndAgentIdOrderByAgreementIdDesc(String contactId, String agentId, Pageable pageable);
	
	Agreement findByAgreementIdAndContactId(String AgreementId, String contactId);
	
	Agreement findByGycbhNumber(String gycbhNumber);
	
	Agreement findByMciAddIdAndStatusPolicyId(String mciAddId, String statusPolicyId);

	List<Agreement> findByMciAddId(String mciAddId);
	
	List<Agreement> findByAgentIdOrderByAgreementIdDesc(String agentId);
	
	Agreement findByGycbhNumberAndAgentId(String gycbhNumber, String agentId);
	
	Agreement findByAgreementIdAndAgentId(String AgreementId, String agentId);
	
	Page<Agreement> findByAgentIdAndStatusPolicyIdInOrderByAgreementIdDesc(String agentId, List<String> status, Pageable pageable);
	
	Agreement findByGycbhNumberAndAgentIdAndOtp(String gycbhNumber, String agentId, String otp);
	
	Agreement findByGycbhNumberAndBaovietDepartmentId(String gycbhNumber, String departmentId);
}