package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.AgencyRelation;
import com.baoviet.agency.dto.AgencyRelationDTO;

/**
 * Mapper for the entity GnocCr and its DTO AgencyRelationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgencyRelationMapper extends EntityMapper <AgencyRelationDTO, AgencyRelation> {
}
