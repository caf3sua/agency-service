package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.ContactDTO;

/**
 * Mapper for the entity GnocCr and its DTO Contact.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactMapper extends EntityMapper <ContactDTO, Contact> {
}
