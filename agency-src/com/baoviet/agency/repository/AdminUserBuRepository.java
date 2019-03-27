package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AdminUserBu;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AdminUserBuRepository extends JpaRepository<AdminUserBu, String> {
	
	AdminUserBu findByAdminId(String id);
}