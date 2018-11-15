package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.PayAction;


/**
 * Spring Data JPA repository for the PayAction module.
 */

@Repository
public interface PayActionRepository extends JpaRepository<PayAction, String>, PayActionRepositoryExtend {

	PayAction findByMciAddId(String mciAddId);

	PayAction findByPolicyNumbers(String policyNumbers);
}