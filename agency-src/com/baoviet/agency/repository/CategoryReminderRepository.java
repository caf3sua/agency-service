package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.CategoryReminder;


/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface CategoryReminderRepository extends JpaRepository<CategoryReminder, String> {
	List<CategoryReminder> findByCode (String code);
}