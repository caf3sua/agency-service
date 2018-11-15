package com.baoviet.agency.service;

import java.net.URISyntaxException;
import java.util.List;

import com.baoviet.agency.dto.AgentReminderDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ReminderCreateVM;
import com.baoviet.agency.web.rest.vm.ReminderSearchVM;

/**
 * Service Interface for managing Agency.
 */
public interface AgentReminderService {

	List<AgentReminderDTO> getAll(String type);
	
	List<AgentReminderDTO> findByContactId (String contactId, String type);
	
	List<AgentReminderDTO> searchReminder(ReminderSearchVM param, String type);
	
	AgentReminderDTO findByReminderId (String reminderId, String type);
	
	AgentReminderDTO create(ReminderCreateVM info, String type) throws AgencyBusinessException;
	
	List<AgentReminderDTO> getCountReminder(String type, Integer numberDay);
	
	void delete(String type, String reminderId) throws URISyntaxException, AgencyBusinessException;
}

