package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Pa;
import com.baoviet.agency.dto.PaDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaMapper extends EntityMapper <PaDTO, Pa> {
}
