package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.TlAnnualRate;
import com.baoviet.agency.dto.TlAnnualRateDTO;

/**
 * Mapper for the entity GnocCr and its DTO TlAnnualRate.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlAnnualRateMapper extends EntityMapper <TlAnnualRateDTO, TlAnnualRate> {
}
