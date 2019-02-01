package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.SktdRate;
import com.baoviet.agency.dto.SktdRateDTO;

/**
 * Mapper for the entity GnocCr and its DTO SktdRate.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SktdRateMapper extends EntityMapper <SktdRateDTO, SktdRate> {
}
