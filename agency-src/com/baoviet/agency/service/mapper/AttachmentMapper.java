package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Attachment;
import com.baoviet.agency.dto.AttachmentDTO;

/**
 * Mapper for the entity GnocCr and its DTO Attachment.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttachmentMapper extends EntityMapper <AttachmentDTO, Attachment> {
}
