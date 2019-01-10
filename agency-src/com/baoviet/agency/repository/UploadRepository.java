package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Upload;


@Repository
public interface UploadRepository extends JpaRepository<Upload, String> {
	
}