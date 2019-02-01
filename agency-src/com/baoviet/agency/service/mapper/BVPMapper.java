package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Bvp;
import com.baoviet.agency.dto.BvpDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BVPMapper extends EntityMapper <BvpDTO, Bvp> {
}
