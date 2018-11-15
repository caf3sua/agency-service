package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.AgentDocument;
import com.baoviet.agency.dto.AgentDocumentDTO;

/**
 * Mapper for the entity GnocCr and its DTO AgentDocument.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgentDocumentMapper extends EntityMapper <AgentDocumentDTO, AgentDocument> {
}
