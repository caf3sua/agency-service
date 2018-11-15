package com.baoviet.agency.service.mapper;

import org.mapstruct.*;

import com.baoviet.agency.domain.KpiOprCmSummary;
import com.baoviet.agency.dto.KpiOprCmSummaryDTO;

/**
 * Mapper for the entity GnocCr and its DTO GnocCr.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GnocCrMapper extends EntityMapper <KpiOprCmSummaryDTO, KpiOprCmSummary> {
}
