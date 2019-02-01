package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.CodeManagement;
import com.baoviet.agency.dto.CodeManagementDTO;

/**
 * Mapper for the entity GnocCr and its DTO CodeManagement.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CodeManagementMapper extends EntityMapper <CodeManagementDTO, CodeManagement> {
}
