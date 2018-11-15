package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.ContactRelationship;
import com.baoviet.agency.dto.ContactRelationshipDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgentContactRelationshipMapper extends EntityMapper <ContactRelationshipDTO, ContactRelationship> {
}
