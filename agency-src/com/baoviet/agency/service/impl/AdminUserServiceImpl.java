package com.baoviet.agency.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.AdminPermission;
import com.baoviet.agency.domain.AdminRole;
import com.baoviet.agency.domain.AdminUser;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.CountOrderDTO;
import com.baoviet.agency.dto.DepartmentDTO;
import com.baoviet.agency.repository.AdminUserRepository;
import com.baoviet.agency.service.AdminUserService;
import com.baoviet.agency.service.mapper.AgreementMapper;
import com.baoviet.agency.web.rest.vm.AdminSearchAgencyVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;


/**
 * Service Implementation for managing GnocCr.
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    private final Logger log = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    @Autowired
    private AdminUserRepository adminUserRepository;
    
    @Autowired
    private AgreementMapper agreementMapper;
    
	@Override
	public AgencyDTO findByEmail(String email) {
		log.debug("Request to findByEmail, {}", email);
		AgencyDTO agencyDTO = new AgencyDTO();
		
		// NamNH: add authorities and role
		AdminUser adminUser = adminUserRepository.findOneByEmailIgnoreCase(email);
		if (adminUser != null) {
			// Set properties
			agencyDTO.setId(adminUser.getId());
			agencyDTO.setMa(adminUser.getId());
			agencyDTO.setEmail(adminUser.getEmail());
			agencyDTO.setTen(adminUser.getFullname());
			agencyDTO.setMatkhau(adminUser.getPassword());
					
			Set<String> authorities = new HashSet<>();
			
			for (AdminRole r : adminUser.getRoles()) {
				// add role
				authorities.add(r.getName());
				// Loop authority
				for (AdminPermission a : r.getAuthorities()) {
					// add permission
					authorities.add(a.getName());
				}
			}
			
			agencyDTO.setAuthorities(authorities);
		}
		
		return agencyDTO;
	}
	
	@Override
	public AdminUser findByEmailAdmin(String email) {
		log.debug("Request to findByEmailAdmin, {}", email);
		
		// NamNH: add authorities and role
		AdminUser adminUser = adminUserRepository.findOneByEmailIgnoreCase(email);
		if (adminUser != null) {
			return adminUser;
		}
		
		return null;
	}
	
	@Override
	public boolean changePassword(String userLogin, String password) {
		log.debug("Request to changePassword, userLogin {}, password {}", userLogin, password);
		AdminUser adminUser = adminUserRepository.findOneByEmailIgnoreCase(userLogin);
		
		if (null == adminUser) {
			return false;
		}
		
		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		// Update mat khau
		adminUser.setPassword(md5Password);
		
		// Save
		adminUserRepository.save(adminUser);
		return true;
	}

	@Override
	public List<AgencyDTO> searchAgency(AdminSearchAgencyVM param, String adminId) {
		return adminUserRepository.searchAgency(param, adminId);
	}
	
	@Override
	public List<DepartmentDTO> searchDepartment (String agentId) {
		return adminUserRepository.searchDepartment(agentId);
	}
	
	@Override
	public List<DepartmentDTO> searchDepartmentByPr(AdminSearchAgencyVM param, String idLogin) {
		return adminUserRepository.searchDepartmentByPr(param, idLogin);
	}
	
	@Override
	public Page<AgreementDTO> searchOrderTransport(SearchAgreementWaitVM obj, String departmentId) {
		Page<AgreementDTO> page = adminUserRepository.searchOrderTransport(obj, departmentId).map(agreementMapper::toDto);
		
		// check thanh toán sau
		calculateIsPaymentLater(page.getContent());
		return page;
	}
	
	@Override
	public Page<AgreementDTO> searchOrderBVWait(SearchAgreementWaitVM obj, String departmentId) {
		Page<AgreementDTO> page = adminUserRepository.searchAdminBVWait(obj, departmentId).map(agreementMapper::toDto);
		return page;
	}
	
	@Override
	public Page<AgreementDTO> searchCartAdmin(SearchAgreementWaitVM obj, String departmentId) {
		Page<AgreementDTO> page = adminUserRepository.searchCartAdmin(obj, departmentId).map(agreementMapper::toDto);
		return page;
	}
	
	@Override
	public CountOrderDTO getAdmCountAllOrder(String departmentId) {
		 CountOrderDTO data = adminUserRepository.getAdmCountAllOrder(departmentId);
		 return data;
	}

    /*
	 * -------------------------------------------------
	 * ---------------- Private method -----------------
	 * -------------------------------------------------
	 */
	
	private void calculateIsPaymentLater(List<AgreementDTO> data) {
		for (AgreementDTO agreementDTO : data) {
			// Check thanh toán sau
			if (StringUtils.equals(agreementDTO.getStatusPolicyId(), "91") &&  StringUtils.equals(AgencyConstants.PAYMENT_METHOD_LATER, agreementDTO.getPaymentMethod())) {
				agreementDTO.setCheckPaymentLater(true);
			}
		}
	}
}
