package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.dto.PayActionDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PayActionMapper extends EntityMapper <PayActionDTO, PayAction> {
}
