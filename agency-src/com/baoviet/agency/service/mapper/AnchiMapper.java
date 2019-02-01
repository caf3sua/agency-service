package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.dto.AnchiDTO;

/**
 * Mapper for the entity AgentReminder
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnchiMapper extends EntityMapper <AnchiDTO, Anchi> {
}
