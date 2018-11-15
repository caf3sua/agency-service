package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.web.rest.vm.AdminSearchAgencyVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface AdminUserRepositoryExtend {

	List<AgencyDTO> searchAgency(AdminSearchAgencyVM param, String adminId);
	
}