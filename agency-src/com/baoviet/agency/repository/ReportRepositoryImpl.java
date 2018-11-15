package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.dto.report.BcDoanhThuAdminDTO;
import com.baoviet.agency.dto.report.BcDoanhThuDTO;
import com.baoviet.agency.dto.report.BcForSaleDTO;
import com.baoviet.agency.dto.report.BcHoaHongAdminDTO;
import com.baoviet.agency.dto.report.BcHoaHongDTO;
import com.baoviet.agency.dto.report.BcHoahongBvpCnDTO;
import com.baoviet.agency.dto.report.BcHoahongBvpHoDTO;
import com.baoviet.agency.dto.report.BcHoahongBvpSaleDTO;
import com.baoviet.agency.dto.report.BcKQKDTrongThangDTO;
import com.baoviet.agency.dto.report.BcKQKinhDoanhHangDTO;
import com.baoviet.agency.dto.report.BcKhaithacBvpDTO;
import com.baoviet.agency.dto.report.BcLuyKeDoanhThuDTO;
import com.baoviet.agency.dto.report.BcSaleKhongDTDTO;
import com.baoviet.agency.dto.report.BcTop10DoanhThuDTO;
import com.baoviet.agency.dto.report.BcTyLeDoanhThuPGDTheoNamDTO;
import com.baoviet.agency.utils.DateUtils;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class ReportRepositoryImpl implements ReportRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<BcHoahongBvpHoDTO> getBcHoahongBvpHo(String p_company, String p_department,
			String p_ngan_hang, String p_chi_nhanh, String p_phong_gd, String p_madaily, String p_ngay_cap_tu,
			String p_ngay_cap_den, String p_ngay_dp_tu, String p_ngay_dp_den) {
		
		// String storeProcedureName = "BAO_CAOPackage.BC_HOAHONG_BVP_HO";
		StoredProcedureQuery query = entityManager
			    .createNamedStoredProcedureQuery("bc_hoahong_bvp_ho")
			    
			    .setParameter("p_company", p_company)
			    .setParameter("p_department", p_department)
			    .setParameter("p_ngan_hang", p_ngan_hang)
			    .setParameter("p_chi_nhanh", p_chi_nhanh)
			    .setParameter("p_phong_gd", p_phong_gd)
			    .setParameter("p_madaily", p_madaily)
			    .setParameter("p_ngay_cap_tu", DateUtils.str2Date(p_ngay_cap_tu))
			    .setParameter("p_ngay_cap_den", DateUtils.str2Date(p_ngay_cap_den))
			    .setParameter("p_ngay_dp_tu", DateUtils.str2Date(p_ngay_dp_tu))
		    	.setParameter("p_ngay_dp_den", DateUtils.str2Date(p_ngay_dp_den));
			    
		query.execute();
		
		List<BcHoahongBvpHoDTO> result = query.getResultList();
		
		return result;
	}
	

	@Override
	@Transactional
	public List<BcHoahongBvpCnDTO> getBcHoahongBvpCn(String p_company, String p_department,
			String p_ngan_hang, String p_chi_nhanh, String p_phong_gd, String p_madaily, String p_ngay_cap_tu,
			String p_ngay_cap_den, String p_ngay_dp_tu, String p_ngay_dp_den) {
		
		// String storeProcedureName = "BAO_CAOPackage.BC_HOAHONG_BVP_CN";
				StoredProcedureQuery query = entityManager
					    .createNamedStoredProcedureQuery("bc_hoahong_bvp_cn")
			    
			    .setParameter("p_company", p_company)
			    .setParameter("p_department", p_department)
			    .setParameter("p_ngan_hang", p_ngan_hang)
			    .setParameter("p_chi_nhanh", p_chi_nhanh)
			    .setParameter("p_phong_gd", p_phong_gd)
			    .setParameter("p_madaily", p_madaily)
			    .setParameter("p_ngay_cap_tu", DateUtils.str2Date(p_ngay_cap_tu))
			    .setParameter("p_ngay_cap_den", DateUtils.str2Date(p_ngay_cap_den))
			    .setParameter("p_ngay_dp_tu", DateUtils.str2Date(p_ngay_dp_tu))
		    	.setParameter("p_ngay_dp_den", DateUtils.str2Date(p_ngay_dp_den));
			    
		query.execute();
		
		List<BcHoahongBvpCnDTO> result = query.getResultList();
		
		return result;
	}
	
	@Override
	@Transactional
	public List<BcHoahongBvpSaleDTO> getBcHoahongBvpSale(String p_company, String p_department,
			String p_ngan_hang, String p_chi_nhanh, String p_phong_gd, String p_madaily, String p_ngay_cap_tu,
			String p_ngay_cap_den, String p_ngay_dp_tu, String p_ngay_dp_den) {
		
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_hoahong_bvp_sale")
		
			    .setParameter("p_company", p_company)
			    .setParameter("p_department", p_department)
			    .setParameter("p_ngan_hang", p_ngan_hang)
			    .setParameter("p_chi_nhanh", p_chi_nhanh)
			    .setParameter("p_phong_gd", p_phong_gd)
			    .setParameter("p_madaily", p_madaily)
			    .setParameter("p_ngay_cap_tu", DateUtils.str2Date(p_ngay_cap_tu))
			    .setParameter("p_ngay_cap_den", DateUtils.str2Date(p_ngay_cap_den))
			    .setParameter("p_ngay_dp_tu", DateUtils.str2Date(p_ngay_dp_tu))
		    	.setParameter("p_ngay_dp_den", DateUtils.str2Date(p_ngay_dp_den));
			    
		query.execute();
		
		List<BcHoahongBvpSaleDTO> result = query.getResultList();
		
		return result;
	}

	
	@Override
	@Transactional
	public List<BcKhaithacBvpDTO> getBcKhaithacBvp(String p_company,
			String p_department, String p_ngan_hang, String p_chi_nhanh, String p_phong_gd, String p_madaily,
			String p_ngay_yc_tu, String p_ngay_yc_den, String p_ngay_hl_tu, String p_ngay_hl_den, String p_ngay_dp_tu,
			String p_ngay_dp_den) {

		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_khai_thac_BVP")

				.setParameter("p_company", p_company)
				.setParameter("p_department", p_department)
				.setParameter("p_ngan_hang", p_ngan_hang)
				.setParameter("p_chi_nhanh", p_chi_nhanh)
				.setParameter("p_phong_gd", p_phong_gd)
				.setParameter("p_madaily", p_madaily)
				.setParameter("p_ngay_yc_tu", DateUtils.str2Date(p_ngay_yc_tu))
				.setParameter("p_ngay_yc_den", DateUtils.str2Date(p_ngay_yc_den))
				.setParameter("p_ngay_hl_tu", DateUtils.str2Date(p_ngay_hl_tu))
				.setParameter("p_ngay_hl_den", DateUtils.str2Date(p_ngay_hl_den))
				.setParameter("p_ngay_dp_tu", DateUtils.str2Date(p_ngay_dp_tu))
				.setParameter("p_ngay_dp_den", DateUtils.str2Date(p_ngay_dp_den));

		query.execute();

		List<BcKhaithacBvpDTO> result = query.getResultList();

		return result;
	}


	@Override
	@Transactional
	public List<BcKQKDTrongThangDTO> getKQKinhTrongThang(String nganHang,
			String chiNhanh, String pgd, Integer thang, Integer nam) {

		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_KQKD_trong_thang")

				.setParameter("p_ngan_hang", nganHang)
				.setParameter("p_chi_nhanh", chiNhanh)
				.setParameter("p_phong_gd", pgd)
				.setParameter("p_month", thang)
				.setParameter("p_year", nam);

		query.execute();

		List<BcKQKDTrongThangDTO> result = query.getResultList();

		return result;
	}

	
	@Override
	@Transactional
	public List<BcSaleKhongDTDTO> getAgencySaleKoPsdt(String p_ngan_hang, String p_chi_nhanh, String p_phong_gd,
			Integer p_month, Integer p_year) {
//		String storeProcedureName = "GYCBHPackage.AGENCY_SALE_KO_PSDT";
		
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_sale_khong_DT")

			.setParameter("p_ngan_hang", p_ngan_hang)
			.setParameter("p_chi_nhanh", p_chi_nhanh)
			.setParameter("p_phong_gd", p_phong_gd)
			.setParameter("p_month", p_month)
			.setParameter("p_year", p_year);

		query.execute();

		List<BcSaleKhongDTDTO> result = query.getResultList();

		return result;
	}

	
	@Override
	@Transactional
	public List<BcTyLeDoanhThuPGDTheoNamDTO> getTyLeDoanhThuPGDTheoNam(String maNganHang, String maChiNhanh,
			String maPGD, Integer nam) {
		// String storeProcedureName = "GYCBHPACKAGE.AGENCY_TYLE_HTKH_PGD";

		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_ty_le_doanh_thu_PGDT_theo_nam")

			.setParameter("p_ngan_hang", maNganHang)
			.setParameter("p_chi_nhanh", maChiNhanh)
			.setParameter("p_phong_gd", maPGD)
			.setParameter("p_year", nam);

		query.execute();

		List<BcTyLeDoanhThuPGDTheoNamDTO> result = query.getResultList();

		return result;
	}
		

	@Override
	@Transactional
	public List<BcForSaleDTO> getForSale(Integer thang, Integer nam) {
		// String storeProcedureName = "GYCBHPackage.AGENCY_PSDT_SALE";

		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_phat_sinh_doanh_thu_thang")

			.setParameter("p_month", thang)
			.setParameter("p_year", nam);

		query.execute();

		List<BcForSaleDTO> result = query.getResultList();

		return result;
	}
	
	
	@Override
	@Transactional
	public List<BcTop10DoanhThuDTO> getTop10DoanhThu(Integer thang, Integer nam) {
		// String storeProcedureName = "GYCBHPackage.AGENCY_TOP_10_CN";
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_top_10_doanh_thu")

			.setParameter("p_month", thang)
			.setParameter("p_year", nam);

		query.execute();

		List<BcTop10DoanhThuDTO> result = query.getResultList();

		return result;
	}


	@Override
	@Transactional
	public List<BcKQKinhDoanhHangDTO> getKQKinhDoanhHang(Integer nam) {
		// String storeProcedureName = "GYCBHPackage.AGENCY_KQKD_toan_hang";
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_kq_kinh_doanh_hang")

			.setParameter("p_year", nam);

		query.execute();

		List<BcKQKinhDoanhHangDTO> result = query.getResultList();

		return result;
	}


	@Override
	@Transactional
	public List<BcLuyKeDoanhThuDTO> GetReportLuyKeTongDoanhThu(String nganHang, String chiNhanh, String pgd,
			String sale, Integer year) {
		// String storeProcedureName = "GYCBHPackage.AGENCY_KQKD_LUYKE";
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_luy_ke_tong_doanh_thu")

			.setParameter("p_ngan_hang", nganHang)
			.setParameter("p_chi_nhanh", chiNhanh)
			.setParameter("p_phong_gd", pgd)
			.setParameter("p_sale", sale)
			.setParameter("p_year", year);

		query.execute();

		List<BcLuyKeDoanhThuDTO> result = query.getResultList();

		return result;
	}

	@Override
	public List<BcHoaHongDTO> getBaoCaoHoaHong(String tuNgay, String denNgay, String agentId) {
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_hoa_hong")

			.setParameter("p_tu_ngay", DateUtils.str2Date(tuNgay))
			.setParameter("p_den_ngay", DateUtils.str2Date(denNgay))
			.setParameter("p_agent_id", agentId);

		query.execute();

		List<BcHoaHongDTO> result = query.getResultList();

		return result;
	}
	
	@Override
	public List<BcHoaHongAdminDTO> getBaoCaoHoaHongAdmin(String tuNgay, String denNgay, String agentId) {
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_hoa_hong_admin")

			.setParameter("p_tu_ngay", DateUtils.str2Date(tuNgay))
			.setParameter("p_den_ngay", DateUtils.str2Date(denNgay))
			.setParameter("p_agent_id", agentId);

		query.execute();

		List<BcHoaHongAdminDTO> result = query.getResultList();

		return result;
	}


	@Override
	public List<BcDoanhThuDTO> getBaoCaoDoanhThu(String tuNgay, String denNgay, String agentId) {
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_doanh_thu")

			.setParameter("p_tu_ngay", DateUtils.str2Date(tuNgay))
			.setParameter("p_den_ngay", DateUtils.str2Date(denNgay))
			.setParameter("p_agent_id", agentId);

		query.execute();

		List<BcDoanhThuDTO> result = query.getResultList();

		return result;
	}
	
	@Override
	public List<BcDoanhThuAdminDTO> getBaoCaoDoanhThuAdmin(String tuNgay, String denNgay, String agentId) {
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("bc_doanh_thu_admin")

			.setParameter("p_tu_ngay", DateUtils.str2Date(tuNgay))
			.setParameter("p_den_ngay", DateUtils.str2Date(denNgay))
			.setParameter("p_agent_id", agentId);

		query.execute();

		List<BcDoanhThuAdminDTO> result = query.getResultList();

		return result;
	}

}