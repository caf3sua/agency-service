package com.baoviet.agency.web.rest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.baoviet.agency.domain.Partner;
import com.baoviet.agency.repository.PartnerRepository;

/**
 * REST controller for Gnoc CR resource.
 */
public abstract class AgencyBaseResource {

    private final Logger log = LoggerFactory.getLogger(AgencyBaseResource.class);

    @Autowired
    private PartnerRepository partnerRepository;
    
    protected int checkTokenCode(String psendAt, String psid, String tokenKey) {
    	// Check psid
    	Partner partner = partnerRepository.findOneByToken(psid);
    	if (partner == null) {
    		return 406;
    	}
    	
    	// Compare md5
//    	String md5Token = DigestUtils.md5DigestAsHex((psid + psendAt).getBytes());
		
		// compare password
//		if (!StringUtils.equals(md5Token, tokenKey)) {
//			log.warn("Wrong token");
//			return 409;
//		}
    	
    	return 0;
    }
}
