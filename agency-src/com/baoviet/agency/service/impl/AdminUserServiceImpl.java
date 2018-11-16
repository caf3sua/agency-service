package com.baoviet.agency.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.baoviet.agency.domain.AdminPermission;
import com.baoviet.agency.domain.AdminRole;
import com.baoviet.agency.domain.AdminUser;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.DepartmentDTO;
import com.baoviet.agency.repository.AdminUserRepository;
import com.baoviet.agency.service.AdminUserService;
import com.baoviet.agency.web.rest.vm.AdminSearchAgencyVM;


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

    /*
	 * -------------------------------------------------
	 * ---------------- Private method -----------------
	 * -------------------------------------------------
	 */
}
