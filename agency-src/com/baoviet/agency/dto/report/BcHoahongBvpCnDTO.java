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
	   @NamedStoredProcedureQuery(name = "bc_hoahong_bvp_cn", 
           procedureName = "BAO_CAOPackage.BC_HOAHONG_BVP_CN",
 		   resultSetMappings = {"bc_hoahong_bvp_cn_mapping"},
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_company", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_department", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngan_hang", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_chi_nhanh", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phong_gd", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_madaily", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_cap_tu", type = Date.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_cap_den", type = Date.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_dp_tu", type = Date.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngay_dp_den", type = Date.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_HH_BVP", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_hoahong_bvp_cn_mapping", classes = {
	        @ConstructorResult(targetClass = BcHoahongBvpCnDTO.class,
	            columns = {
	                @ColumnResult(name="baoviet_company_name",type = String.class),
	                @ColumnResult(name="address",type = String.class),
	                @ColumnResult(name="tenphong",type = String.class),
	                @ColumnResult(name="tennhom",type = String.class),
	                @ColumnResult(name="tendaily",type = String.class),
	                @ColumnResult(name="madaily",type = String.class),
	                @ColumnResult(name="khach_hang_ten",type = String.class),
	                @ColumnResult(name="ndbh_ten",type = String.class),
	                @ColumnResult(name="so_gycbh",type = String.class),
	                @ColumnResult(name="send_date",type = String.class),
	                @ColumnResult(name="trang_thai_hd",type = String.class),
	                @ColumnResult(name="so_hd",type = String.class),
	                @ColumnResult(name="so_gcn",type = String.class),
	                @ColumnResult(name="thbh_tu",type = String.class),
	                @ColumnResult(name="thbh_den",type = String.class),
	                @ColumnResult(name="phi_bh",type = Long.class),
	                @ColumnResult(name="tang_giam_phi",type = Long.class),
	                @ColumnResult(name="phi_phai_thu",type = Long.class),
	                @ColumnResult(name="phi_thuc_thu",type = Long.class),
	                @ColumnResult(name="ngay_nop_phi",type = String.class),
	                @ColumnResult(name="ngay_cap_don",type = String.class),
	                @ColumnResult(name="so_giao_dich",type = String.class),
	                @ColumnResult(name="ghi_chu",type = String.class),
	                @ColumnResult(name="hoa_hong_sale",type = Long.class),
	                @ColumnResult(name="hoa_hong_truong_dv",type = Long.class),
	                @ColumnResult(name="hoa_hong_dv",type = Long.class),
	                @ColumnResult(name="tong_hoa_hong",type = Long.class)
	            }
	        )
	})
})
public class BcHoahongBvpCnDTO implements Serializable {
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
	private String tenNdbh;
	private String soGycbh;
	private String sendDate;
	private String trangThaiHd;
	private String soHd;
	private String soGcn;
	private String thbhTu;
	private String thbhDen;
	private Long phiBh;
	private Long tangGiamPhi;
	private Long phiPhaiThu;
	private Long phiThucThu;
	private String ngayNopPhi;
	private String ngayCapDon;
	private String soGiaoDich;
	private String ghiChu;
	private Long hoaHongSale;
	private Long hoaHongTruongDv;
	private Long hoaHongDv;
	private Long tongHoaHong;
	public BcHoahongBvpCnDTO(String baovietCompanyName, String address, String tenPhong, String tenNhom,
			String tenDaiLy, String maDaiLy, String tenKhachHang, String tenNdbh, String soGycbh, String sendDate,
			String trangThaiHd, String soHd, String soGcn, String thbhTu, String thbhDen, Long phiBh,
			Long tangGiamPhi, Long phiPhaiThu, Long phiThucThu, String ngayNopPhi, String ngayCapDon,
			String soGiaoDich, String ghiChu, Long hoaHongSale, Long hoaHongTruongDv, Long hoaHongDv,
			Long tongHoaHong) {
		super();
		this.baovietCompanyName = baovietCompanyName;
		this.address = address;
		this.tenPhong = tenPhong;
		this.tenNhom = tenNhom;
		this.tenDaiLy = tenDaiLy;
		this.maDaiLy = maDaiLy;
		this.tenKhachHang = tenKhachHang;
		this.tenNdbh = tenNdbh;
		this.soGycbh = soGycbh;
		this.sendDate = sendDate;
		this.trangThaiHd = trangThaiHd;
		this.soHd = soHd;
		this.soGcn = soGcn;
		this.thbhTu = thbhTu;
		this.thbhDen = thbhDen;
		this.phiBh = phiBh;
		this.tangGiamPhi = tangGiamPhi;
		this.phiPhaiThu = phiPhaiThu;
		this.phiThucThu = phiThucThu;
		this.ngayNopPhi = ngayNopPhi;
		this.ngayCapDon = ngayCapDon;
		this.soGiaoDich = soGiaoDich;
		this.ghiChu = ghiChu;
		this.hoaHongSale = hoaHongSale;
		this.hoaHongTruongDv = hoaHongTruongDv;
		this.hoaHongDv = hoaHongDv;
		this.tongHoaHong = tongHoaHong;
	}
}