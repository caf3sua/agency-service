package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class AgencyDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String addrCode;

	private String agencyP1Id;

	private String agencyP1Name;

	private String agencyP2Id;

	private String agencyP2Name;

	private String agencyP3Id;

	private String agencyP3Name;

	private String agencyP4Id;

	private String agencyP4Name;

	private String agencyP5Id;

	private String agencyP5Name;

	private String agencyP6Id;

	private String agencyP6Name;

	private String chiCucThue;

	private String createdBy;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createdDate;

	private String cvdl;

	private String diaChiChiTiet;

	private String dienThoai;

	private String dienThoaiCq;

	private String dienThoaiNr;

	private String dlgt;

	private String email;

	private String gioiTinh;

	private String htlv;

	private String idOld;

	private String kenhPhanPhoi;

	private String kinhNghiem;

	private String loaiDaiLy;

	private String loaiDiaChi;

	private String loaiGt;

	private String loaiTk;

	private String lyDoBn;

	private String lyDoStatus;

	private String ma;

	private String maBanNt;

	private String maCbkt;

	private String maCbql;

	private String maDlNt;

	private String maDonVi;

	private String maNhomNt;

	private String maSoThue;

	private String matkhau;

	private String modifiedBy;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date modifiedDate;

	private String nganHang;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayBoNhiem;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayCap;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayCapCc;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayCapCcCd;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayCapCcNc;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayCapCnCb;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayCapCnSp;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayCapMst;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayHlCc;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayHlCcCd;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayHlCcNc;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayHlCnCb;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayHlCnSp;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayHlHddl;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayKtCc;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayKtCcCd;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayKtCcNc;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayKtCnCb;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayKtCnSp;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayKyHddl;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngaySinh;

	private String ngheNghiep;

	private String nguonTd;

	private String noiCap;

	private String noiSinh;

	private String pttt;

	private String qtLv;

	private String quanHuyen;

	private String quocGia;

	private String roleId;

	private String roleName;

	private String soCc;

	private String soCcCd;

	private String soCcNc;

	private String soCnCb;

	private String soCnSp;

	private String soFax;

	private String soGt;

	private String soHddl;

	private String soTheDl;

	private String soTk;

	private String status;

	private String tdhv;

	private String ten;

	private String tenCbkt;

	private String tenCbql;

	private String tenChuTk;

	private String tenThoaThuan;

	private String tinhThanh;

	private String token;

	private String ttgd;

	private String tvvTd;

	private String urn;

	private String xaPhuong;
	
	private Set<String> authorities;
	
	private Integer sendOtp;
	
	private List<DepartmentDTO> lstDepartment;
}