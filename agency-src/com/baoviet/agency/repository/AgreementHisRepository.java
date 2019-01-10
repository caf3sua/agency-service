package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AgreementHis;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AgreementHisRepository extends JpaRepository<AgreementHis, String>{
	
	List<AgreementHis> findByGycbhNumberAndAgentId(String gycbhNumber, String agentId);
	
	List<AgreementHis> findByGycbhNumberAndAgentIdAndStatusPolicyIdNotIn(String gycbhNumber, String agentId, List<String> lstStatus);
	
	List<AgreementHis> findByGycbhNumberAndBaovietDepartmentId(String gycbhNumber, String departmentId);
}