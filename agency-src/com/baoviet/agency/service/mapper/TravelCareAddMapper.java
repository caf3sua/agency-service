package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.TravelCareAdd;
import com.baoviet.agency.dto.TravelCareAddDTO;

/**
 * Mapper for the entity GnocCr and its TravelCareAdd
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelCareAddMapper extends EntityMapper <TravelCareAddDTO, TravelCareAdd> {
}
