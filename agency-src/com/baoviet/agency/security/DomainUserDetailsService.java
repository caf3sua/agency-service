package com.baoviet.agency.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.domain.Permission;
import com.baoviet.agency.domain.Role;
import com.baoviet.agency.repository.AgencyRepository;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    @Autowired
    private AgencyRepository agencyRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        
        // Optional<User> userFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
        Agency agency = agencyRepository.findOneByEmailIgnoreCase(login);
        
        if (agency == null) {
        	throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }
        
        // grantedAuthorities
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		// Set default role ROLE_AGENCY
        GrantedAuthority defaultAuthority = new SimpleGrantedAuthority(AgencyConstants.ROLE_AGENCY);
        grantedAuthorities.add(defaultAuthority);
        
        for (Role r : agency.getRoles()) {
        	// check admin
        	if (StringUtils.equals(r.getName(), AgencyConstants.ROLE_ADMIN) ) {
        		GrantedAuthority adminAuthority = new SimpleGrantedAuthority(AgencyConstants.ROLE_ADMIN);
                grantedAuthorities.add(adminAuthority);
        	} else {
        		for (Permission a : r.getAuthorities()) {
            		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(a.getName());
                    grantedAuthorities.add(grantedAuthority);
    			}
        	}
        }
        
        return new org.springframework.security.core.userdetails.User(lowercaseLogin,
        		agency.getMatkhau(),
                grantedAuthorities);
    }
}
