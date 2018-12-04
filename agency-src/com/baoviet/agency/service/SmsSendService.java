package com.baoviet.agency.service;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.SmsSendDTO;

/**
 * Service Interface for managing Anchi.
 */
public interface SmsSendService {
	
	String save(SmsSendDTO param);
	
	void sendSMS(AgreementDTO agreement, Contact contact, String phone, String content);
	
}

