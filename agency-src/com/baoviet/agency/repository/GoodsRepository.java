package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Goods;


/**
 * Spring Data JPA repository for the Goods
 */

@Repository
public interface GoodsRepository extends JpaRepository<Goods, String> {
	
}