package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AgentReminder;


/**
 * Spring Data JPA repository AgentReminder
 */

@Repository
public interface AgentReminderRepository extends JpaRepository<AgentReminder, String>, AgentReminderRepositoryExtend {
	
	List<AgentReminder> findByActiveAndType(String active, String type);
	
	List<AgentReminder> findByType(String type);
	
	List<AgentReminder> findByContactIdAndType(String contactId, String type);
	
	AgentReminder findByIdAndType(String reminderId, String type);
	
	long deleteByContactId(String contactId);
}