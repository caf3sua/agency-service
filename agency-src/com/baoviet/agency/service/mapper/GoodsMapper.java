package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.Goods;
import com.baoviet.agency.dto.GoodsDTO;

/**
 * Mapper for the entity GnocCr and its Goods
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoodsMapper extends EntityMapper <GoodsDTO, Goods> {
}
