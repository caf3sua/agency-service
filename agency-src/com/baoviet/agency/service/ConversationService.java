package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.ConversationDTO;
import com.baoviet.agency.web.rest.vm.ConversationVM;

/**
 * Service Interface for managing Conversation.
 */
public interface ConversationService {
	String insertConversation(ConversationDTO info);
	
	List<ConversationDTO> getByParrentId(String agreementId);
	
	ConversationDTO save(ConversationVM info, AgencyDTO currentAgency, AgreementDTO agreement);
	
	ConversationDTO saveAdmin(ConversationVM info, AgencyDTO currentAgency, AgreementDTO agreement);
}

