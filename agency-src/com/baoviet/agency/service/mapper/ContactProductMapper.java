package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.ContactProduct;
import com.baoviet.agency.dto.ContactProductDTO;

/**
 * Mapper for the entity GnocCr and its DTO ContactProduct.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactProductMapper extends EntityMapper <ContactProductDTO, ContactProduct> {
}
