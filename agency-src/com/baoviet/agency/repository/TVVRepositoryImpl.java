package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.dto.report.BcKQKDTrongThangDTO;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class TVVRepositoryImpl implements TVVRepository {

	@PersistenceContext
	private EntityManager entityManager;

//	@Override
//	@Transactional
//	public List<BcKQKDTrongThangDTO> getKQKinhTrongThang(String nganHang, String chiNhanh, String pgd, Integer thang,
//			Integer nam) {
//
//		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_KQKD_trong_thang")
//
//				.setParameter("p_ngan_hang", nganHang).setParameter("p_chi_nhanh", chiNhanh)
//				.setParameter("p_phong_gd", pgd).setParameter("p_month", thang).setParameter("p_year", nam);
//
//		query.execute();
//
//		List<BcKQKDTrongThangDTO> result = query.getResultList();
//
//		return result;
//	}

}