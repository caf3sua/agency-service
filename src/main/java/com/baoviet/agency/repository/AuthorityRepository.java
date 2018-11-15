package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baoviet.agency.domain.Permission;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Permission, String> {
}
