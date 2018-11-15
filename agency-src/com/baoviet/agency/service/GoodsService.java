package com.baoviet.agency.service;


import com.baoviet.agency.dto.GoodsDTO;

/**
 * Service Interface for managing GoodsService.
 */
public interface GoodsService {
	
	Integer insertGoods(GoodsDTO info);
	
	GoodsDTO save(GoodsDTO goodsDTO);
	
	GoodsDTO getById(String id);
}

