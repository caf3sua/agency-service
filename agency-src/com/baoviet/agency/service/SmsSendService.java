package com.baoviet.agency.service;

import com.baoviet.agency.dto.SmsSendDTO;

/**
 * Service Interface for managing Anchi.
 */
public interface SmsSendService {
	
	String save(SmsSendDTO param);
	
	int sendSMS(String phone, String content);
	
}

