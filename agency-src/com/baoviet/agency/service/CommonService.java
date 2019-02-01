package com.baoviet.agency.service;

import java.net.URISyntaxException;

import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.SendMaillVM;

/**
 * Service Interface for managing Common.
 */
public interface CommonService {
	SendMaillVM sendMail(SendMaillVM param) throws URISyntaxException, AgencyBusinessException;
}

