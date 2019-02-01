package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.CarRate;
import com.baoviet.agency.dto.CarRateDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarRateMapper extends EntityMapper <CarRateDTO, CarRate> {
}
