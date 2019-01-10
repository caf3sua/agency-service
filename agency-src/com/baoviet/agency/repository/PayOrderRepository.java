package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.PayOrder;


/**
 * Spring Data JPA repository for the PayAction module.
 */

@Repository
public interface PayOrderRepository extends JpaRepository<PayOrder, String> {
	PayOrder findByMciAddId(String mciAddId);
}