package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.TlAdd;
import com.baoviet.agency.dto.TlAddDTO;

/**
 * Mapper for the entity GnocCr and its TlDTO Tl.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlAddMapper extends EntityMapper <TlAddDTO, TlAdd> {
}
