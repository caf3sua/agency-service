package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.TmpMomoCar;
import com.baoviet.agency.dto.TmpMomoCarDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TmpMomoCarMapper extends EntityMapper <TmpMomoCarDTO, TmpMomoCar> {
}
