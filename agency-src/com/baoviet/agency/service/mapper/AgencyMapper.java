package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.dto.AgencyDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgencyMapper extends EntityMapper <AgencyDTO, Agency> {
}
