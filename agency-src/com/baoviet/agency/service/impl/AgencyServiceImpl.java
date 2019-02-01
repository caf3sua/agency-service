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

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.domain.Permission;
import com.baoviet.agency.domain.Role;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.repository.AgencyRepository;
import com.baoviet.agency.service.AgencyService;
import com.baoviet.agency.service.mapper.AgencyMapper;


/**
 * Service Implementation for managing GnocCr.
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class AgencyServiceImpl implements AgencyService {

    private final Logger log = LoggerFactory.getLogger(AgencyServiceImpl.class);

    @Autowired
	private AgencyMapper agencyMapper;
    
    @Autowired
    private AgencyRepository agencyRepository;
    
	@Override
	public AgencyDTO save(AgencyDTO agencyDTO) {
		log.debug("Request to save agency, {}", agencyDTO);
		// Convert to Entity
		Agency entity = agencyMapper.toEntity(agencyDTO);
		Agency result = agencyRepository.save(entity);
		return agencyMapper.toDto(result);
	}

	@Override
	public AgencyDTO findByEmail(String email) {
		log.debug("Request to findByEmail, {}", email);
		AgencyDTO agencyDTO = null;
		
		// NamNH: add authorities and role
		Agency agency = agencyRepository.findOneByEmailIgnoreCase(email);
		if (agency != null) {
			agencyDTO = agencyMapper.toDto(agency);
					
			Set<String> authorities = new HashSet<>();
			// Add default ROLE_AGENCY
			authorities.add(AgencyConstants.ROLE_AGENCY);
			
			for (Role r : agency.getRoles()) {
				// add role
				authorities.add(r.getName());
				// Loop authority
				for (Permission a : r.getAuthorities()) {
					// add permission
					authorities.add(a.getName());
				}
			}
			
			agencyDTO.setAuthorities(authorities);
		}
		
		return agencyDTO;
	}

	@Override
	public boolean changePassword(String userLogin, String password) {
		log.debug("Request to changePassword, userLogin {}, password {}", userLogin, password);
		Agency agency = agencyRepository.findOneByEmailIgnoreCase(userLogin);
		
		if (null == agency) {
			return false;
		}
		
		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		// Update mat khau
		agency.setMatkhau(md5Password);
		
		// Save
		agencyRepository.save(agency);
		return true;
	}

	@Override
	public List<Agency> getAgencyByParent(String parent) {
		log.debug("Request to getAgencyByParent, {}", parent);
		List<Agency> data = agencyRepository.findByMaDonVi(parent);
		return data;
	}

	@Override
	public Set<String> authoritiesToStrings(String email) {
		log.debug("Request to authoritiesToStrings, {}", email);
		Set<String> authorities = new HashSet<>();
		Agency agency = agencyRepository.findOneByEmailIgnoreCase(email);
		if (agency != null) {
			for (Role r : agency.getRoles()) {
				// Loop authority
				for (Permission a : r.getAuthorities()) {
					authorities.add(a.getName());
				}
			}
		}
		
		return authorities;
	}

    /*
	 * -------------------------------------------------
	 * ---------------- Private method -----------------
	 * -------------------------------------------------
	 */
}
