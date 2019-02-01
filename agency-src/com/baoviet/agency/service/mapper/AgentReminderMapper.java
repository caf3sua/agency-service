package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.AgentReminder;
import com.baoviet.agency.dto.AgentReminderDTO;

/**
 * Mapper for the entity AgentReminder
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgentReminderMapper extends EntityMapper <AgentReminderDTO, AgentReminder> {
}
