package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.BenifitTravel;
import com.baoviet.agency.dto.BenifitTravelDTO;

/**
 * Mapper for the entity GnocCr and its DTO BenifitTravel.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BenifitTravelMapper extends EntityMapper <BenifitTravelDTO, BenifitTravel> {
}
