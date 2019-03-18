package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.CountOrderDTO;
import com.baoviet.agency.dto.DepartmentDTO;
import com.baoviet.agency.web.rest.vm.AdminSearchAgencyVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface AdminUserRepositoryExtend {

	List<AgencyDTO> searchAgency(AdminSearchAgencyVM param, String adminId);
	
	List<DepartmentDTO> searchDepartmentByPr(AdminSearchAgencyVM param, String idLogin);
	
	List<DepartmentDTO> searchDepartment (String agentId);
	
	
	Page<Agreement> searchOrderTransport(SearchAgreementWaitVM param, String departmentId);	// đơn hàng đã cấp
	
	Page<Agreement> searchAdminBVWait(SearchAgreementWaitVM param, String departmentId);
	
	Page<Agreement> searchCartAdmin(SearchAgreementWaitVM param, String departmentId);
	
	CountOrderDTO getAdmCountAllOrder(String departmentId);
	
}