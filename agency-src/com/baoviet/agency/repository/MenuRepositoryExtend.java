package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.dto.MenuDTO;


/**
 * Spring Data JPA repository for the Menu module.
 */
@Repository
public interface MenuRepositoryExtend {
	
	List<MenuDTO> getRootMenuCollectionAgency(int level, String roleName, String site); 
}