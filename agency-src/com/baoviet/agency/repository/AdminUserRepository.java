package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.AdminUser;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, String>, AdminUserRepositoryExtend {
	AdminUser findOneByEmailIgnoreCase(String email);
}