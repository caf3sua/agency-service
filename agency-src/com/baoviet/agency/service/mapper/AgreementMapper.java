package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.dto.AgreementDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgreementMapper extends EntityMapper <AgreementDTO, Agreement> {
}
