package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Tvicare;
import com.baoviet.agency.dto.TvicareDTO;

/**
 * Mapper for the entity GnocCr and its TvicareDTO
 */
@Mapper(componentModel = "spring", uses = {})
public interface TvicareMapper extends EntityMapper <TvicareDTO, Tvicare> {
}
