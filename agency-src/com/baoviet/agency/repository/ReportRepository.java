package com.baoviet.agency.repository;

import java.util.List;

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

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public interface ReportRepository {
	List<BcHoahongBvpHoDTO> getBcHoahongBvpHo(String company, String department, String nganHang, String chiNhanh,
			String phongGd, String madaily, String ngaycaptu, String ngaycapden, String ngaydptu, String ngaydpden);

	List<BcHoahongBvpCnDTO> getBcHoahongBvpCn(String company, String department, String nganHang, String chiNhanh,
			String phongGd, String madaily, String ngaycaptu, String ngaycapden, String ngaydptu, String ngaydpden);

	List<BcHoahongBvpSaleDTO> getBcHoahongBvpSale(String company, String department,
			String nganHang, String chiNhanh, String phongGd, String madaily, String ngaycaptu, String ngaycapden,
			String ngaydptu, String ngaydpden);

	List<BcKhaithacBvpDTO> getBcKhaithacBvp(String p_company, String p_department,
			String p_ngan_hang, String p_chi_nhanh, String p_phong_gd, String p_madaily, String p_ngay_yc_tu,
			String p_ngay_yc_den, String p_ngay_hl_tu, String p_ngay_hl_den, String p_ngay_dp_tu, String p_ngay_dp_den);

	List<BcKQKDTrongThangDTO> getKQKinhTrongThang(String nganHang, String chiNhanh, String pgd, Integer thang, Integer nam);

	List<BcSaleKhongDTDTO> getAgencySaleKoPsdt(String p_ngan_hang, String p_chi_nhanh, String p_phong_gd, Integer p_month, Integer p_year);

	List<BcTyLeDoanhThuPGDTheoNamDTO> getTyLeDoanhThuPGDTheoNam(String maNganHang, String maChiNhanh, String maPGD, Integer nam);

	List<BcForSaleDTO> getForSale(Integer thang, Integer nam);

	List<BcTop10DoanhThuDTO> getTop10DoanhThu(Integer thang, Integer nam);

	List<BcKQKinhDoanhHangDTO> getKQKinhDoanhHang(Integer nam);

	List<BcLuyKeDoanhThuDTO> GetReportLuyKeTongDoanhThu(String nganHang, String chiNhanh, String pgd, String sale, Integer year);
	
	List<BcHoaHongDTO> getBaoCaoHoaHong(String tuNgay, String denNgay, String agentId);
	
	List<BcDoanhThuDTO> getBaoCaoDoanhThu(String tuNgay, String denNgay, String agentId);
	
	List<BcDoanhThuAdminDTO> getBaoCaoDoanhThuAdmin(String tuNgay, String denNgay, String adminId);
	
	List<BcHoaHongAdminDTO> getBaoCaoHoaHongAdmin(String tuNgay, String denNgay, String adminId);
}