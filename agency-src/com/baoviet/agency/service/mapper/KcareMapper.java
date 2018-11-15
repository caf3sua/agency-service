package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Kcare;
import com.baoviet.agency.dto.KcareDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KcareMapper extends EntityMapper <KcareDTO, Kcare> {
}
