package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.PurposeOfUsage;
import com.baoviet.agency.dto.PurposeOfUsageDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PurposeOfUsageMapper extends EntityMapper <PurposeOfUsageDTO, PurposeOfUsage> {
}
