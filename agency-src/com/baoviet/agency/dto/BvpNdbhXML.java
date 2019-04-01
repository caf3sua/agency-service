package com.baoviet.agency.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
public class BvpNdbhXML implements Serializable {
	private static final long serialVersionUID = 1L;

	private String NGUOIDBH_NAME;
	private String NGUOIDBH_DIACHITHUONGTRU;
	private String NGUOIDBH_NGAYSINH;
	private String NGUOIDBH_CMND;
	private String NGUOIDBH_QUANHE;
	private String NGUOIDBH_GIOITINH;
	private String NGUOIDBH_DIENTHOAI;
	private String NGUOIDBH_EMAIL;
	private String CHUONG_TRINH;
	private String STBH_CTC;
	private String STBH_QLBS_1;
	private String TNCN;
	private String SINHMANG_SOTIENBH;
	private String STBH_QLBS_4;
	private String STBH_QLBS_TS;
	private String THBH_TU;
	private String THBH_DEN;
	private String TONGPHI_PHI;
	private String TANGGIAM_PHI;
	private String TONG_PHI;
	private String TONG_PHI_CHU;
	private String CHANGE_PREMIUM;
	private String NGUOITH_NAME;
	private String NGUOITH_CMND;
	private String NGUOITH_QUANHE;
	private String NGUOITH_DIENTHOAI;
	private String NGUOITH_EMAIL;
	private String NGUOITH_DIACHI;
	private String NGUOINHAN_NAME;
	private String NGUOINHAN_CMND;
	private String NGUOINHAN_QUANHE;
	private String NGUOINHAN_DIENTHOAI;
	private String NGUOINHAN_EMAIL;
	private String NGUOINHAN_DIACHI;
	private String NGAYCAP;
	private String GHICHU;
	private String Q1;
	private String Q2;
	private String Q3;
	private String MUCDONGCHITRA;
	private String DIEU_KHOAN_CHINH;
	private String CHITIETPHIBH_NGOAICHU;
	private String CHITIETPHIBH_TAINAN;
	private String CHITIETPHIBH_SINHMANG;
	private String CHITIETPHIBH_NHAKHOA;
	private String CHITIETPHIBH_THAISAN;
}