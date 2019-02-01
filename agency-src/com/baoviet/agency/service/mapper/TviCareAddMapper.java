package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.TviCareAdd;
import com.baoviet.agency.dto.TviCareAddDTO;

/**
 * Mapper for the entity GnocCr and its TviCareAddDTO
 */
@Mapper(componentModel = "spring", uses = {})
public interface TviCareAddMapper extends EntityMapper <TviCareAddDTO, TviCareAdd> {
}
