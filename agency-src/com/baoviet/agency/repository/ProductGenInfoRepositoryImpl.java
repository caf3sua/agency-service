package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.ProductGenInfo;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class ProductGenInfoRepositoryImpl implements ProductGenInfoRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ProductGenInfo> getByProductId(String productGenId) {
		String expression = "";

		expression = "SELECT * FROM (SELECT * FROM PRODUCT_GEN_INFO i  " +
        "WHERE i.STATUS = 1 AND i.FK_PRODUCT_GEN_ID = '" + productGenId + "' ORDER BY i.SORT) T1 " +
        "UNION ALL   " +
        "SELECT * FROM (SELECT * FROM PRODUCT_GEN_INFO i  " +
        "WHERE i.STATUS = 1 AND i.FK_PRODUCT_GEN_IDS LIKE '%" + productGenId + "%' ORDER BY i.SORT) T2 ";
		
		Query query = entityManager.createNativeQuery(expression, ProductGenInfo.class);

		List<ProductGenInfo> data = query.getResultList();

		if (data != null && data.size() > 0) {
			return data;
		}
		return null;
	}

	@Override
	public List<ProductGenInfo> getProductGenInfosforhomepage(String pSite) {
		String expression = "";

		expression = "SELECT * from product_gen_info ip WHERE ip.fk_product_gen_id in (select PRODUCT_GEN_ID from PRODUCT_GEN where IS_HOME='True' and SITE =:pSite) ORDER BY ip.issue_date DESC";
		
		Query query = entityManager.createNativeQuery(expression, ProductGenInfo.class);
		
		query.setParameter("pSite", pSite);

		List<ProductGenInfo> data = query.getResultList();

		if (data != null && data.size() > 0) {
			return data;
		}
		return null;
	}

}