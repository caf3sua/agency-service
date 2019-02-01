package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.TvcPlaneAdd;
import com.baoviet.agency.dto.TvcPlaneAddDTO;

/**
 * Mapper for the entity GnocCr and its TlDTO Tl.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TvcPlaneAddMapper extends EntityMapper <TvcPlaneAddDTO, TvcPlaneAdd> {
}
