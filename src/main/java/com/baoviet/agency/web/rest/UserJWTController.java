package com.baoviet.agency.web.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.AdminPermission;
import com.baoviet.agency.domain.AdminRole;
import com.baoviet.agency.domain.AdminUser;
import com.baoviet.agency.domain.Permission;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AdminUserRepository;
import com.baoviet.agency.security.jwt.JWTConfigurer;
import com.baoviet.agency.security.jwt.TokenProvider;
import com.baoviet.agency.web.rest.AccountController.JWTToken;
import com.baoviet.agency.web.rest.vm.AgencyLoginVM;
import com.baoviet.agency.web.rest.vm.LoginVM;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController extends AgencyBaseResource {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private Environment env;
    
    @Autowired
	private AdminUserRepository adminUserRepository;

    @PostMapping(value={"/authenticate", "/agency/account/login"})
    @ApiResponses({ @ApiResponse(code = 403, message = "Token invalids"),
		@ApiResponse(code = 406, message = "psid not exits"),
		@ApiResponse(code = 403, message = "TokenKey is invalid") })
    @ApiOperation(value = "authorize", notes = "Hàm thực hiện login cho Agency/Admin")
    @Timed
    public ResponseEntity authorize(@Valid @RequestBody AgencyLoginVM loginVM, HttpServletResponse response) throws AgencyBusinessException {
    	Boolean casEnable = Boolean.valueOf(env.getProperty("spring.cas.enabled"));
    	
    	if (casEnable) {
        	// Login with CAS
        	log.debug("REST request to login/authorize with CAS server");
        	return authorizeWithCAS(loginVM, response);    		
    	} else {
        	// Login locally
        	log.debug("REST request to login/authorize at local");
        	return authorizeLocally(loginVM, response);
    	}
    }
    
    private ResponseEntity authorizeWithCAS(AgencyLoginVM loginVM, HttpServletResponse response) {
    	// Get configuration info
    	String casTicketUrl = env.getProperty("spring.cas.ticket-url");
    	
    	RestTemplate restTemplate = new RestTemplate();
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    	MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
    	map.add("username", loginVM.getUsername());
    	String md5Password = DigestUtils.md5DigestAsHex(loginVM.getPassword().toString().getBytes());
    	map.add("password", md5Password);

    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

    	ResponseEntity<String> result = restTemplate.postForEntity( casTicketUrl, request , String.class );
    	
    	String ticket = result.getBody();
    	
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	SimpleGrantedAuthority userAuthoruty = new SimpleGrantedAuthority("ROLE_USER");
    	authorities.add(userAuthoruty);
    	
    	UsernamePasswordAuthenticationToken authenticationToken =
    				new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
    	org.springframework.security.core.userdetails.User user = new 
    			org.springframework.security.core.userdetails.User(loginVM.getUsername(), loginVM.getPassword(), authorities);
    	//authenticationToken.setAuthenticated(true);
    	authenticationToken.setDetails(user);
    	
      try {
	      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	      boolean rememberMe = true;
	      String jwt = tokenProvider.createToken(authenticationToken, rememberMe);
	      response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
	      return ResponseEntity.ok(new JWTToken(jwt));
	  } catch (AuthenticationException ae) {
	      log.trace("Authentication exception trace: {}", ae);
	      return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
	          ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
	  }
    }
    
    private ResponseEntity authorizeLocally(AgencyLoginVM loginVM, HttpServletResponse response) throws AgencyBusinessException {
    	if (Boolean.TRUE.equals(loginVM.getIsAdmin())) {
			// Admin Baoviet
			return loginAsAdmin(loginVM, response);
		} else {
			// Agency
			return loginAsAgency(loginVM, response);
		}
    }

    private ResponseEntity loginAsAgency(AgencyLoginVM loginVM, HttpServletResponse response) throws AgencyBusinessException{
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), loginVM.getPassword());

		try {
			Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			boolean rememberMe = true;
			String jwt = tokenProvider.createToken(authentication, rememberMe);
			response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
			return ResponseEntity.ok(new JWTToken(jwt));
		} catch (AuthenticationException ae) {
			log.trace("Authentication exception trace: {}", ae);
			throw new AgencyBusinessException("email - password", ErrorCode.INVALID , "Email hoặc Mật khẩu không chính xác.");
		}
	} 
	
	private ResponseEntity loginAsAdmin(AgencyLoginVM loginVM, HttpServletResponse response) throws AgencyBusinessException {
		// Get configuration info
		String lowercaseLogin = loginVM.getUsername().toLowerCase(Locale.ENGLISH);
        
        // Optional<User> userFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
        AdminUser adminUser = adminUserRepository.findOneByEmailIgnoreCase(lowercaseLogin);
        
        if (adminUser == null) {
        	throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }
        
        // zcompare password
        String md5Password = DigestUtils.md5DigestAsHex(loginVM.getPassword().toString().getBytes());
        if (!StringUtils.equals(adminUser.getPassword(), md5Password)) {
        	throw new AgencyBusinessException("password", ErrorCode.INVALID , "Mật khẩu không chính xác.");
        }
        
        // grantedAuthorities
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		// Set default role ROLE_AGENCY
        GrantedAuthority baovietAuthority = new SimpleGrantedAuthority(AgencyConstants.ROLE_BAOVIET);
        grantedAuthorities.add(baovietAuthority);
        
        for (AdminRole r : adminUser.getRoles()) {
        	if (StringUtils.equals(r.getName(), AgencyConstants.ROLE_ADMIN) ) {
        		GrantedAuthority adminAuthority = new SimpleGrantedAuthority(AgencyConstants.ROLE_ADMIN);
                grantedAuthorities.add(adminAuthority);
        	} else {
        		for (AdminPermission a : r.getAuthorities()) {
            		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(a.getName());
                    grantedAuthorities.add(grantedAuthority);
    			}
        	}
    		
        }
        
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), loginVM.getPassword(), grantedAuthorities);
		org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
				loginVM.getUsername(), loginVM.getPassword(), grantedAuthorities);
		authenticationToken.setDetails(user);

		try {
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			boolean rememberMe = true;
			String jwt = tokenProvider.createToken(authenticationToken, rememberMe);
			response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
			return ResponseEntity.ok(new JWTToken(jwt));
		} catch (AuthenticationException ae) {
			log.trace("Authentication exception trace: {}", ae);
			return new ResponseEntity<>(Collections.singletonMap("AuthenticationException", ae.getLocalizedMessage()),
					HttpStatus.UNAUTHORIZED);
		}
	}
	
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
