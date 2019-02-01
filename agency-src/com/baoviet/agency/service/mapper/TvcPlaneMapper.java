package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.TvcPlane;
import com.baoviet.agency.dto.TvcPlaneDTO;

/**
 * Mapper for the entity GnocCr and its TlDTO Tl.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TvcPlaneMapper extends EntityMapper <TvcPlaneDTO, TvcPlane> {
}
