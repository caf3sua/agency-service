package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.TinhtrangSk;
import com.baoviet.agency.dto.TinhtrangSkDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TinhtrangSkMapper extends EntityMapper <TinhtrangSkDTO, TinhtrangSk> {
}
