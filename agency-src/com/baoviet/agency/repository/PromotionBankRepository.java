package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.PromotionBank;


@Repository
public interface PromotionBankRepository extends JpaRepository<PromotionBank, String> {
	List<PromotionBank> findByActive(String active);
}