package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.GoodsBasicRate;
import com.baoviet.agency.dto.GoodsBasicRateDTO;

/**
 * Mapper for the entity GnocCr and its DTO GoodsBasicRate.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoodsBasicRateMapper extends EntityMapper <GoodsBasicRateDTO, GoodsBasicRate> {
}
