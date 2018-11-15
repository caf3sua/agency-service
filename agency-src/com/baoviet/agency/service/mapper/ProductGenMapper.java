package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.ProductGen;
import com.baoviet.agency.dto.ProductGenDTO;

/**
 * Mapper for the entity GnocCr and its DTO ProductGen.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductGenMapper extends EntityMapper <ProductGenDTO, ProductGen> {
}
