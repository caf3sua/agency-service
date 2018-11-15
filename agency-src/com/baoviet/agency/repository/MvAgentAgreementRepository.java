package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.MvAgentAgreement;


/**
 * Spring Data JPA repository MvAgentAgreement
 */

@Repository
public interface MvAgentAgreementRepository extends JpaRepository<MvAgentAgreement, String> {
	List<MvAgentAgreement> findByAgentCode(String agentId);
	
	List<MvAgentAgreement> findByDepartmentCode(String departmentId);
	
	List<MvAgentAgreement> findByAgentCodeAndDepartmentCode(String agentId, String departmentId);
	
	List<MvAgentAgreement> findByDepartmentCodeAndAgentNameLike(String departmentId, String keyword);
}