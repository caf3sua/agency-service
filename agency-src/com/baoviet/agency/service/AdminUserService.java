package com.baoviet.agency.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.baoviet.agency.domain.AdminUser;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.CountOrderDTO;
import com.baoviet.agency.dto.DepartmentDTO;
import com.baoviet.agency.web.rest.vm.AdminSearchAgencyVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;

/**
 * Service Interface for managing Agency.
 */
public interface AdminUserService {
	
	AgencyDTO findByEmail(String email);
	
	AdminUser findByEmailAdmin(String email);
	
	boolean changePassword(String userLogin, String password);
	
	List<AgencyDTO> searchAgency (AdminSearchAgencyVM param, String adminId);
	
	List<DepartmentDTO> searchDepartmentByPr (AdminSearchAgencyVM param, String idLogin);
	
	List<DepartmentDTO> searchDepartment (String agentId);
	
	CountOrderDTO getAdmCountAllOrder(String departmentId);
	
	Page<AgreementDTO> searchOrderTransport(SearchAgreementWaitVM obj, String departmentId);
	
	Page<AgreementDTO> searchOrderBVWait(SearchAgreementWaitVM obj, String departmentId);
	
	Page<AgreementDTO> searchCartAdmin(SearchAgreementWaitVM obj, String departmentId);
}

