package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Moto;
import com.baoviet.agency.dto.MotoDTO;

/**
 * Mapper for the entity GnocCr and its MotoDTO
 */
@Mapper(componentModel = "spring", uses = {})
public interface MotoMapper extends EntityMapper <MotoDTO, Moto> {
}
