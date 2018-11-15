package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.RePostcodeVn;
import com.baoviet.agency.dto.RePostcodeVnDTO;

/**
 * Mapper for the entity GnocCr and its DTO RePostcodeVn.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RePostcodeVnMapper extends EntityMapper <RePostcodeVnDTO, RePostcodeVn> {
}
