package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Menu;


/**
 * Spring Data JPA repository for the Menu entity.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, String>, MenuRepositoryExtend {
	
}