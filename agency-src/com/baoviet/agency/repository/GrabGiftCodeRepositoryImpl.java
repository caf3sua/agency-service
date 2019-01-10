package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.GrabGiftCode;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class GrabGiftCodeRepositoryImpl implements GrabGiftCodeRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<GrabGiftCode> getGrabGiftCodeByCount(int count) {
		String sql = "select * from grab_giftcode ga where ga.giftcode_code is null and rownum <= :count";
		Query query = entityManager.createNativeQuery(sql, GrabGiftCode.class);
		query.setParameter("count", count);
		List<GrabGiftCode> lst = query.getResultList();
		if (lst != null && lst.size() > 0) {
			return lst;
		} else {
			return null;
		}
	}
}