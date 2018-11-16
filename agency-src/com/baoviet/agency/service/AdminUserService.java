package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.domain.AdminUser;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.DepartmentDTO;
import com.baoviet.agency.web.rest.vm.AdminSearchAgencyVM;

/**
 * Service Interface for managing Agency.
 */
public interface AdminUserService {
	
	AgencyDTO findByEmail(String email);
	
	AdminUser findByEmailAdmin(String email);
	
	boolean changePassword(String userLogin, String password);
	
	List<AgencyDTO> searchAgency (AdminSearchAgencyVM param, String adminId);
	
	List<DepartmentDTO> searchDepartment (String agentId);
}

