package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.TlRate;
import com.baoviet.agency.dto.TlRateDTO;

/**
 * Mapper for the entity GnocCr and its DTO TlRate.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlRateMapper extends EntityMapper <TlRateDTO, TlRate> {
}
