package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Promotion;
import com.baoviet.agency.dto.PromotionDTO;

/**
 * Mapper for the entity GnocCr and its Promotion.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PromotionMapper extends EntityMapper <PromotionDTO, Promotion> {
}
