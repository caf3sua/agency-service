package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Goods;
import com.baoviet.agency.dto.GoodsDTO;
import com.baoviet.agency.repository.GoodsRepository;
import com.baoviet.agency.service.GoodsService;
import com.baoviet.agency.service.mapper.GoodsMapper;

/**
 * Service Implementation for managing Goods.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class GoodsServiceImpl implements GoodsService {

	private final Logger log = LoggerFactory.getLogger(FilesServiceImpl.class);

	@Autowired
	private GoodsRepository GoodsRepository;

	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public Integer insertGoods(GoodsDTO info) {
		log.debug("Request to insertGoods : {} ", info);
		if (info != null) {
			try {
				Goods entity = GoodsRepository.save(goodsMapper.toEntity(info));
				return Integer.parseInt(entity.getHhId());

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public GoodsDTO save(GoodsDTO GoodsDTO) {
		log.debug("Request to save : {} ", GoodsDTO);
		// Convert to Entity
		Goods entity = goodsMapper.toEntity(GoodsDTO);
		Goods result = GoodsRepository.save(entity);
		return goodsMapper.toDto(result);
	}

	@Override
	public GoodsDTO getById(String id) {
		log.debug("Request to getById - GoodsDTO: id{} ", id);
		Goods result = GoodsRepository.findOne(id);
		return goodsMapper.toDto(result);
	}

}
