package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Conversation;
import com.baoviet.agency.dto.ConversationDTO;

/**
 * Mapper for the entity GnocCr and its DTO Conversation.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConversationMapper extends EntityMapper <ConversationDTO, Conversation> {
}
