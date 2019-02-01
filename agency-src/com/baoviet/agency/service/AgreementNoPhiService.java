package com.baoviet.agency.service;

import java.net.URISyntaxException;

import com.baoviet.agency.dto.AgreementNoPhiDTO;
import com.baoviet.agency.exception.AgencyBusinessException;

/**
 * Service Interface for managing Contact.
 */
public interface AgreementNoPhiService {
	
	AgreementNoPhiDTO save(AgreementNoPhiDTO agreementNoPhiDTO);
	
	AgreementNoPhiDTO getByAgreementId(String agreementId, String contactId);
	
	void delete(String type, String id) throws URISyntaxException, AgencyBusinessException;
}

