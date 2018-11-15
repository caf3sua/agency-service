package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Home;
import com.baoviet.agency.dto.HomeDTO;

/**
 * Mapper for the entity GnocCr and its Home TlAnnualRate.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HomeMapper extends EntityMapper <HomeDTO, Home> {
}
