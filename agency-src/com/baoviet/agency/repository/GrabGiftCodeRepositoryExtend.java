package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.GrabGiftCode;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface GrabGiftCodeRepositoryExtend {
	public List<GrabGiftCode> getGrabGiftCodeByCount(int count);
}