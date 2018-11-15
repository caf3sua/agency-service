package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the BVP_HIS database table.
 * 
 */
@Entity
@Table(name="BVP_HIS")
@Getter
@Setter
public class BvpHi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "BVP_HIS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ID", unique=true)
	private String id;
	
	@Column(name="BAOVIET_DEPARTMENT_ID")
	private String baovietDepartmentId;

	@Column(name="BAOVIET_DEPARTMENT_NAME")
	private String baovietDepartmentName;

	@Column(name="BAOVIET_ID")
	private String baovietId;

	@Column(name="BAOVIET_NAME")
	private String baovietName;

	@Column(name="CHUONGTRINH_BH")
	private String chuongtrinhBh;

	@Column(name="CHUONGTRINH_PHI")
	private Double chuongtrinhPhi;

	@Column(name="CONTACT_ADDRESS")
	private String contactAddress;

	@Column(name="CONTACT_CODE")
	private String contactCode;

	@Column(name="CONTACT_EMAIL")
	private String contactEmail;

	@Column(name="CONTACT_ID")
	private String contactId;

	@Column(name="CONTACT_PHONE")
	private String contactPhone;

	@Column(name="COUPONS_CODE")
	private String couponsCode;

	@Column(name="COUPONS_VALUE")
	private Double couponsValue;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_PAYMENT")
	private Date dateOfPayment;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column
	private String ghichu;

	@Temporal(TemporalType.DATE)
	@Column(name="INCEPTION_DATE")
	private Date inceptionDate;

	@Column
	private String ngoaitru;

	@Column(name="NGOAITRU_PHI")
	private Double ngoaitruPhi;

	@Column(name="NGUOIDBH_CMND")
	private String nguoidbhCmnd;

	@Column(name="NGUOIDBH_DIACHITHUONGTRU")
	private String nguoidbhDiachithuongtru;

	@Column(name="NGUOIDBH_GIOITINH")
	private String nguoidbhGioitinh;

	@Column(name="NGUOIDBH_NAME")
	private String nguoidbhName;

	@Temporal(TemporalType.DATE)
	@Column(name="NGUOIDBH_NGAYSINH")
	private Date nguoidbhNgaysinh;

	@Column(name="NGUOIDBH_NGHENGHIEP")
	private String nguoidbhNghenghiep;

	@Column(name="NGUOIDBH_QUANHE")
	private String nguoidbhQuanhe;

	@Column(name="NGUOINHAN_CMND")
	private String nguoinhanCmnd;

	@Column(name="NGUOINHAN_DIACHI")
	private String nguoinhanDiachi;

	@Column(name="NGUOINHAN_DIENTHOAI")
	private String nguoinhanDienthoai;

	@Column(name="NGUOINHAN_EMAIL")
	private String nguoinhanEmail;

	@Column(name="NGUOINHAN_NAME")
	private String nguoinhanName;

	@Column(name="NGUOINHAN_QUANHE")
	private String nguoinhanQuanhe;

	@Temporal(TemporalType.DATE)
	@Column(name="NGUOINT_NGAYSINH")
	private Date nguointNgaysinh;

	@Column(name="NGUOITH_CMND")
	private String nguoithCmnd;

	@Column(name="NGUOITH_DIACHI")
	private String nguoithDiachi;

	@Column(name="NGUOITH_DIENTHOAI")
	private String nguoithDienthoai;

	@Column(name="NGUOITH_EMAIL")
	private String nguoithEmail;

	@Column(name="NGUOITH_NAME")
	private String nguoithName;

	@Temporal(TemporalType.DATE)
	@Column(name="NGUOITH_NGAYSINH")
	private Date nguoithNgaysinh;

	@Column(name="NGUOITH_QUANHE")
	private String nguoithQuanhe;

	@Column(name="NGUOIYC_CMND")
	private String nguoiycCmnd;

	@Column(name="NGUOIYC_DIACHINHANTHU")
	private String nguoiycDiachinhanthu;

	@Column(name="NGUOIYC_DIACHITHUONGTRU")
	private String nguoiycDiachithuongtru;

	@Column(name="NGUOIYC_DIENTHOAI")
	private String nguoiycDienthoai;

	@Column(name="NGUOIYC_EMAIL")
	private String nguoiycEmail;

	@Column(name="NGUOIYC_NAME")
	private String nguoiycName;

	@Temporal(TemporalType.DATE)
	@Column(name="NGUOIYC_NGAYSINH")
	private Date nguoiycNgaysinh;

	@Column
	private String nhakhoa;

	@Column(name="NHAKHOA_PHI")
	private Double nhakhoaPhi;

	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;

	@Temporal(TemporalType.DATE)
	@Column(name="POLICY_DELIVER")
	private Date policyDeliver;

	@Column(name="POLICY_NUMBER")
	private String policyNumber;

	@Column(name="POLICY_OLD")
	private String policyOld;

	@Column(name="POLICY_PARENT")
	private String policyParent;

	@Column
	private String q1;

	@Column(name="Q1_ID")
	private String q1Id;

	@Column
	private String q2;

	@Column(name="Q2_ID")
	private String q2Id;

	@Column
	private String q3;

	@Column(name="Q3_ID")
	private String q3Id;

	@Column
	private String q4;

	@Column(name="Q4_ID")
	private String q4Id;

	@Column
	private String q5;

	@Column(name="Q5_ID")
	private String q5Id;

	@Column
	private String q6;

	@Column(name="Q6_ID")
	private String q6Id;

	@Column(name="RECEIVER_ADDRESS")
	private String receiverAddress;

	@Column(name="RECEIVER_EMAIL")
	private String receiverEmail;

	@Column(name="RECEIVER_MOIBLE")
	private String receiverMoible;

	@Column(name="RECEIVER_NAME")
	private String receiverName;

	@Temporal(TemporalType.DATE)
	@Column(name="RESPONSE_DATE")
	private Date responseDate;

	@Temporal(TemporalType.DATE)
	@Column(name="SEND_DATE")
	private Date sendDate;

	@Column
	private String sinhmang;

	@Column(name="SINHMANG_PHI")
	private Double sinhmangPhi;

	@Column(name="SINHMANG_SOTIENBH")
	private Double sinhmangSotienbh;

	@Temporal(TemporalType.DATE)
	@Column(name="SMARTAPP_SYSDATE")
	private Date smartappSysdate;

	@Column
	private String sogycbh;

	@Column(name="STATUS_POLICY_ID")
	private String statusPolicyId;

	@Column(name="STATUS_POLICY_NAME")
	private String statusPolicyName;

	@Column(name="TANGGIAM_PHI")
	private Double tanggiamPhi;

	@Column(name="TANGGIAM_PHI_NOIDUNG")
	private String tanggiamPhiNoidung;

	@Column
	private String thaisan;

	@Column(name="THAISAN_PHI")
	private Double thaisanPhi;

	@Column
	private String tncn;

	@Column(name="TNCN_PHI")
	private Double tncnPhi;

	@Column(name="TNCN_SOTIENBH")
	private Double tncnSotienbh;

	@Column(name="TONGPHI_PHI")
	private Double tongphiPhi;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;

}