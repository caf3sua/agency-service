package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP_HIS database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BvpHiDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String baovietDepartmentId;

	private String baovietDepartmentName;

	private String baovietId;

	private String baovietName;

	private String chuongtrinhBh;

	private Double chuongtrinhPhi;

	private String contactAddress;

	private String contactCode;

	private String contactEmail;

	private String contactId;

	private String contactPhone;

	private String couponsCode;

	private Double couponsValue;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfPayment;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate;

	private String ghichu;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate;

	private String ngoaitru;

	private Double ngoaitruPhi;

	private String nguoidbhCmnd;

	private String nguoidbhDiachithuongtru;

	private String nguoidbhGioitinh;

	private String nguoidbhName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date nguoidbhNgaysinh;

	private String nguoidbhNghenghiep;

	private String nguoidbhQuanhe;

	private String nguoinhanCmnd;

	private String nguoinhanDiachi;

	private String nguoinhanDienthoai;

	private String nguoinhanEmail;

	private String nguoinhanName;

	private String nguoinhanQuanhe;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date nguointNgaysinh;

	private String nguoithCmnd;

	private String nguoithDiachi;

	private String nguoithDienthoai;

	private String nguoithEmail;

	private String nguoithName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date nguoithNgaysinh;

	private String nguoithQuanhe;

	private String nguoiycCmnd;

	private String nguoiycDiachinhanthu;

	private String nguoiycDiachithuongtru;

	private String nguoiycDienthoai;

	private String nguoiycEmail;

	private String nguoiycName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date nguoiycNgaysinh;

	private String nhakhoa;

	private Double nhakhoaPhi;

	private String paymentMethod;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policyDeliver;

	private String policyNumber;

	private String policyOld;

	private String policyParent;

	private String q1;

	private String q1Id;

	private String q2;

	private String q2Id;

	private String q3;

	private String q3Id;

	private String q4;

	private String q4Id;

	private String q5;

	private String q5Id;

	private String q6;

	private String q6Id;

	private String receiverAddress;

	private String receiverEmail;

	private String receiverMoible;

	private String receiverName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date responseDate;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date sendDate;

	private String sinhmang;

	private Double sinhmangPhi;

	private Double sinhmangSotienbh;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date smartappSysdate;

	private String sogycbh;

	private String statusPolicyId;

	private String statusPolicyName;

	private Double tanggiamPhi;

	private String tanggiamPhiNoidung;

	private String thaisan;

	private Double thaisanPhi;

	private String tncn;

	private Double tncnPhi;

	private Double tncnSotienbh;

	private Double tongphiPhi;

	private String userId;

	private String userName;

}