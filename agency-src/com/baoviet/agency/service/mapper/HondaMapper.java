package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.MotoHonda;
import com.baoviet.agency.dto.MotoHondaDTO;

/**
 * Mapper for the entity GnocCr and its MotoDTO
 */
@Mapper(componentModel = "spring", uses = {})
public interface HondaMapper extends EntityMapper <MotoHondaDTO, MotoHonda> {
}
