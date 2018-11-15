package com.baoviet.agency.dto.report;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.StoredProcedureParameter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
@AllArgsConstructor
@Entity
@NamedStoredProcedureQueries({
	   @NamedStoredProcedureQuery(name = "bc_khai_thac_BVP", 
           procedureName = "BAO_CAOPackage.KT_HANG_NGAY_BVP",
 		   resultSetMappings = {"bc_khai_thac_BVP_mapping"},
           parameters = {
        		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_company", type = String.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_department", type = String.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngan_hang", type = String.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_chi_nhanh", type = String.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phong_gd", type = String.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_madaily", type = String.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_yc_tu", type = Date.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_yc_den", type = Date.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_hl_tu", type = Date.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_hl_den", type = Date.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_dp_tu", type = Date.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_dp_den", type = Date.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_KT_BVP", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_khai_thac_BVP_mapping", classes = {
	        @ConstructorResult(targetClass = BcKhaithacBvpDTO.class,
	            columns = {
	            		@ColumnResult(name="baoviet_company_name",type = String.class),
		                @ColumnResult(name="address",type = String.class),
		                @ColumnResult(name="tenphong",type = String.class),
		                @ColumnResult(name="tennhom",type = String.class),
		                @ColumnResult(name="tendaily",type = String.class),
		                @ColumnResult(name="madaily",type = String.class),
		                @ColumnResult(name="khach_hang_ten",type = String.class),
		                @ColumnResult(name="khach_hang_cmt",type = String.class),
		                @ColumnResult(name="khach_hang_dia_chi_ll",type = String.class),
		                @ColumnResult(name="khach_hang_dia_chi_nt",type = String.class),
		                @ColumnResult(name="khach_hang_mobile",type = String.class),
		                @ColumnResult(name="khach_hang_phone",type = String.class),
		                @ColumnResult(name="khach_hang_email",type = String.class),
		                @ColumnResult(name="ndbh_ten",type = String.class),
		                @ColumnResult(name="ndbh_cmt",type = String.class),
		                @ColumnResult(name="ndbh_ngay_sinh",type = String.class),
		                @ColumnResult(name="so_gycbh",type = String.class),
		                @ColumnResult(name="send_date",type = String.class),
		                @ColumnResult(name="trang_thai_hd",type = String.class),
		                @ColumnResult(name="so_hd",type = String.class),
		                @ColumnResult(name="so_gcn",type = String.class),
		                @ColumnResult(name="ngay_cap_don",type = String.class),
		                @ColumnResult(name="ngay_gui_gycbh",type = String.class),
		                @ColumnResult(name="thbh_tu",type = String.class),
		                @ColumnResult(name="thbh_den",type = String.class),
		                @ColumnResult(name="chuong_trinh",type = String.class),
		                @ColumnResult(name="ngoai_tru",type = String.class),
		                @ColumnResult(name="tai_nan_cn",type = String.class),
		                @ColumnResult(name="sinh_mang_cn",type = String.class),
		                @ColumnResult(name="nha_khoa",type = String.class),
		                @ColumnResult(name="thai_san",type = String.class),
		                @ColumnResult(name="phi_bh",type = Long.class),
		                @ColumnResult(name="tang_giam_phi",type = Long.class),
		                @ColumnResult(name="phi_phai_thu",type = Long.class),
		                @ColumnResult(name="phi_thuc_thu",type = Long.class),
		                @ColumnResult(name="ngay_nop_phi",type = String.class),
		                @ColumnResult(name="so_giao_dich",type = String.class),
		                @ColumnResult(name="ghi_chu",type = String.class)
	            }
	        )
	})
})
public class BcKhaithacBvpDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String baovietCompanyName; 
	private String address;
	private String tenPhong;
	private String tenNhom;
	private String tenDaiLy;
	private String maDaiLy;
	private String tenKhachHang;
	private String cmtKhachHang;
	private String diaChiLLKhachHang;
	private String diaChiNTKhachHang;
	private String mobileKhachHang;
	private String phoneKhachHang;
	private String emailKhachHang;
	private String tenNdbh;
	private String cmtNdbh;
	private String ngaySinhNdbh;
	private String soGycbh;
	private String sendDate;
	private String trangThaiHd;
	private String soHd;
	private String soGcn;
	private String ngayCapDon;
	private String ngayGuiGycbh;
	private String thbhTu;
	private String thbhDen;
	private String chuongTrinh;
	private String ngoaiTru;
	private String taiNanCN;
	private String sinhMangCN;
	private String nhaKhoa;
	private String thaiSan;
	private Long phiBh;
	private Long tangGiamPhi;
	private Long phiPhaiThu;
	private Long phiThucThu;
	private String ngayNopPhi;
	private String soGiaoDich;
	private String ghiChu;
	
	public BcKhaithacBvpDTO(String baovietCompanyName, String address, String tenPhong, String tenNhom, String tenDaiLy,
			String maDaiLy, String tenKhachHang, String cmtKhachHang, String diaChiLLKhachHang,
			String diaChiNTKhachHang, String mobileKhachHang, String phoneKhachHang, String emailKhachHang,
			String tenNdbh, String cmtNdbh, String ngaySinhNdbh, String soGycbh, String sendDate, String trangThaiHd,
			String soHd, String soGcn, String ngayCapDon, String ngayGuiGycbh, String thbhTu, String thbhDen,
			String chuongTrinh, String ngoaiTru, String taiNanCN, String sinhMangCN, String nhaKhoa, String thaiSan,
			Long phiBh, Long tangGiamPhi, Long phiPhaiThu, Long phiThucThu, String ngayNopPhi, String soGiaoDich,
			String ghiChu) {
		super();
		this.baovietCompanyName = baovietCompanyName;
		this.address = address;
		this.tenPhong = tenPhong;
		this.tenNhom = tenNhom;
		this.tenDaiLy = tenDaiLy;
		this.maDaiLy = maDaiLy;
		this.tenKhachHang = tenKhachHang;
		this.cmtKhachHang = cmtKhachHang;
		this.diaChiLLKhachHang = diaChiLLKhachHang;
		this.diaChiNTKhachHang = diaChiNTKhachHang;
		this.mobileKhachHang = mobileKhachHang;
		this.phoneKhachHang = phoneKhachHang;
		this.emailKhachHang = emailKhachHang;
		this.tenNdbh = tenNdbh;
		this.cmtNdbh = cmtNdbh;
		this.ngaySinhNdbh = ngaySinhNdbh;
		this.soGycbh = soGycbh;
		this.sendDate = sendDate;
		this.trangThaiHd = trangThaiHd;
		this.soHd = soHd;
		this.soGcn = soGcn;
		this.ngayCapDon = ngayCapDon;
		this.ngayGuiGycbh = ngayGuiGycbh;
		this.thbhTu = thbhTu;
		this.thbhDen = thbhDen;
		this.chuongTrinh = chuongTrinh;
		this.ngoaiTru = ngoaiTru;
		this.taiNanCN = taiNanCN;
		this.sinhMangCN = sinhMangCN;
		this.nhaKhoa = nhaKhoa;
		this.thaiSan = thaiSan;
		this.phiBh = phiBh;
		this.tangGiamPhi = tangGiamPhi;
		this.phiPhaiThu = phiPhaiThu;
		this.phiThucThu = phiThucThu;
		this.ngayNopPhi = ngayNopPhi;
		this.soGiaoDich = soGiaoDich;
		this.ghiChu = ghiChu;
	}
	
}