package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.PaAdd;
import com.baoviet.agency.dto.PaAddDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaAddMapper extends EntityMapper <PaAddDTO, PaAdd> {
}
