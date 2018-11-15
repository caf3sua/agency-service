package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Tl;
import com.baoviet.agency.dto.TlDTO;

/**
 * Mapper for the entity GnocCr and its TlDTO Tl.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlMapper extends EntityMapper <TlDTO, Tl> {
}
