package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Travelcare;
import com.baoviet.agency.dto.TravelcareDTO;

/**
 * Mapper for the entity GnocCr and its Travelcare
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelcareMapper extends EntityMapper <TravelcareDTO, Travelcare> {
}
