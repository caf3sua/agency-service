package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.ProductGenInfo;
import com.baoviet.agency.dto.ProductGenInfoDTO;

/**
 * Mapper for the entity GnocCr and its ProductGenInfo
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductGenInfoMapper extends EntityMapper <ProductGenInfoDTO, ProductGenInfo> {
}
