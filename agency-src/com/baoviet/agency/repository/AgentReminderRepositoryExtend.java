package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AgentReminder;
import com.baoviet.agency.web.rest.vm.ReminderSearchVM;

/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface AgentReminderRepositoryExtend {

	List<AgentReminder> searchReminder(ReminderSearchVM param, String type);
	
	List<AgentReminder> getCountReminder(String type, Integer numberDay);
	
}