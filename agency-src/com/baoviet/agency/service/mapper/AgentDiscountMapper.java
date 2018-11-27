package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.AgentDiscount;
import com.baoviet.agency.dto.AgentDiscountDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgentDiscountMapper extends EntityMapper <AgentDiscountDTO, AgentDiscount> {
}
