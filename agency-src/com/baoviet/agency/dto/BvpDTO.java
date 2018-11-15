package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BvpDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String baovietDepartmentId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietDepartmentName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String chuongtrinhBh = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double chuongtrinhPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String contactAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactPhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String couponsCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double couponsValue = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfPayment = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String ghichu = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String invoiceAccountNo = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceBuyer = AgencyConstants.DEFAULT_STRING_VALUE;

	private Integer invoiceCheck = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String invoiceCompany = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceTaxNo = AgencyConstants.DEFAULT_STRING_VALUE;

	private String ngoaitru = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double ngoaitruPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String nguoidbhCmnd = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoidbhDiachithuongtru = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoidbhGioitinh = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoidbhName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date nguoidbhNgaysinh = AgencyConstants.DEFAULT_DOB_VALUE;

	private String nguoidbhNghenghiep = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoidbhQuanhe = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoinhanCmnd = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoinhanDiachi = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoinhanDienthoai = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoinhanEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoinhanName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoinhanQuanhe = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date nguointNgaysinh = AgencyConstants.DEFAULT_DOB_VALUE;

	private String nguoithCmnd = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoithDiachi = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoithDienthoai = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoithEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoithName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date nguoithNgaysinh = AgencyConstants.DEFAULT_DOB_VALUE;

	private String nguoithQuanhe = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoiycCmnd = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoiycDiachinhanthu = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoiycDiachithuongtru = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoiycDienthoai = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoiycEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String nguoiycName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date nguoiycNgaysinh = AgencyConstants.DEFAULT_DOB_VALUE;

	private String nhakhoa = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double nhakhoaPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String paymentMethod = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policyDeliver = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String policyNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String policyOld = AgencyConstants.DEFAULT_STRING_VALUE;

	private String policyParent = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q1 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q1Id = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q2 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q2Id = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q3 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q3Id = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q4 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q4Id = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q5 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q5Id = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q6 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q6Id = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverMoible = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date responseDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date sendDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String sinhmang = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double sinhmangPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double sinhmangSotienbh = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date smartappSysdate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String sogycbh = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusPolicyId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusPolicyName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double tanggiamPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String tanggiamPhiNoidung = AgencyConstants.DEFAULT_STRING_VALUE;

	private String thaisan = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double thaisanPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String tncn = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double tncnPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double tncnSotienbh = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double tongphiPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String userId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String userName = AgencyConstants.DEFAULT_STRING_VALUE;

}