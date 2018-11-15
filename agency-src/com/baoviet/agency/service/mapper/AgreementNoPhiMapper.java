package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.AgreementNoPhi;
import com.baoviet.agency.dto.AgreementNoPhiDTO;

/**
 * Mapper for the entity GnocCr and its DTO AgreementNoPhi.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgreementNoPhiMapper extends EntityMapper <AgreementNoPhiDTO, AgreementNoPhi> {
}
