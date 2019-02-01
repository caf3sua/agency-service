package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Upload;
import com.baoviet.agency.dto.UploadDTO;

/**
 * Mapper for the entity GnocCr and its Upload
 */
@Mapper(componentModel = "spring", uses = {})
public interface UploadMapper extends EntityMapper <UploadDTO, Upload> {
}
