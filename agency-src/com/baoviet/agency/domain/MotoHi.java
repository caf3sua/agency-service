package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the MOTO_HIS database table.
 * 
 */
@Entity
@Table(name="MOTO_HIS")
@Getter
@Setter
public class MotoHi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "MOTO_HIS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;

	@Temporal(TemporalType.DATE)
	@Column(name="BV_SYSDATE")
	private Date bvSysdate;

	@Column(name="CHAYNO_PHI")
	private Double chaynoPhi;

	@Column(name="CHAYNO_STBH")
	private Double chaynoStbh;

	@Column(name="COMPANY_ID")
	private String companyId;

	@Column(name="COMPNAY_NAME")
	private String compnayName;

	@Column(name="CONTACT_CODE")
	private String contactCode;

	@Column(name="CONTACT_ID")
	private String contactId;

	@Column(name="CONTACT_USERNAME")
	private String contactUsername;

	@Column(name="COUPONS_CODE")
	private String couponsCode;

	@Column(name="COUPONS_VALUE")
	private Double couponsValue;

	@Column(name="CUSTOMER_ADDRESS")
	private String customerAddress;

	@Column(name="CUSTOMER_EMAIL")
	private String customerEmail;

	@Column(name="CUSTOMER_ID_NUMBER")
	private String customerIdNumber;

	@Column(name="CUSTOMER_NAME")
	private String customerName;

	@Column(name="CUSTOMER_PHONE")
	private String customerPhone;

	@Column(name="DEPARTMENT_ID")
	private String departmentId;

	@Column(name="DEPARTMENT_NAME")
	private String departmentName;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column
	private String ghichu;

	@Temporal(TemporalType.DATE)
	@Column(name="INCEPTION_DATE")
	private Date inceptionDate;

	@Column(name="INSURED_ADDRESS")
	private String insuredAddress;

	@Column(name="INSURED_EMAIL")
	private String insuredEmail;

	@Column(name="INSURED_NAME")
	private String insuredName;

	@Column(name="INSURED_PHONE")
	private String insuredPhone;

	@Column
	private String make;

	@Column
	private String model;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_NOP_PHI")
	private Date ngayNopPhi;

	@Column(name="PARENT_ID")
	private String parentId;

	@Column(name="POLICY_NUMBER")
	private String policyNumber;

	@Column(name="POLICY_STATUS")
	private String policyStatus;

	@Column(name="POLICY_STATUS_NAME")
	private String policyStatusName;

	@Column(name="RECEIVER_ADDRESS")
	private String receiverAddress;

	@Column(name="RECEIVER_EMAIL")
	private String receiverEmail;

	@Column(name="RECEIVER_MOIBLE")
	private String receiverMoible;

	@Column(name="RECEIVER_NAME")
	private String receiverName;

	@Column(name="REGISTRATION_NUMBER")
	private String registrationNumber;

	@Column(name="SO_GYCBH")
	private String soGycbh;

	@Column(name="SO_GYCBH_ID")
	private String soGycbhId;

	@Column
	private String sokhung;

	@Column
	private String somay;

	@Column
	private String status;

	@Column(name="STATUS_NAME")
	private String statusName;

	@Column(name="TNDS_BB_PHI")
	private Double tndsBbPhi;

	@Column(name="TNDS_TN_NGUOI")
	private Double tndsTnNguoi;

	@Column(name="TNDS_TN_NNTX_PHI")
	private Double tndsTnNntxPhi;

	@Column(name="TNDS_TN_PHI")
	private Double tndsTnPhi;

	@Column(name="TNDS_TN_TS")
	private Double tndsTnTs;

	@Column(name="TONG_PHI")
	private Double tongPhi;

	@Column(name="TYPE_OF_MOTO_ID")
	private String typeOfMotoId;

	@Column(name="TYPE_OF_MOTO_NAME")
	private String typeOfMotoName;

	@Column(name="USER_NHAP")
	private String userNhap;

	@Column(name="USER_NHAP_NAME")
	private String userNhapName;

	@Column(name="VCX_PHI")
	private Double vcxPhi;

	@Column(name="VCX_STBH")
	private Double vcxStbh;

}