package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Attachment;


/**
 * Spring Data JPA repository for the Attachment module.
 */

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String>{
	List<Attachment> findByParrentId(String conversationId);
	
	long deleteByParrentId(String conversationId);
}