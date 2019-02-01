package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.GoodsContainerizedRate;
import com.baoviet.agency.dto.GoodsContainerizedRateDTO;

/**
 * Mapper for the entity GnocCr and its DTO GoodsContainerizedRate.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoodsContainerizedRateMapper extends EntityMapper <GoodsContainerizedRateDTO, GoodsContainerizedRate> {
}
