package com.baoviet.agency.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.GiftCode;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class GiftCodeRepositoryImpl implements GiftCodeRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public int updateGiftCodesByIds(String ids) {
		String sql = "UPDATE GIFTCODE g SET g.ACTIVE = '1' WHERE g.GIFTCODE IN (:ids)";
		Query query = entityManager.createNativeQuery(sql, GiftCode.class);
		query.setParameter("ids", ids);
		return query.executeUpdate();
	}
}