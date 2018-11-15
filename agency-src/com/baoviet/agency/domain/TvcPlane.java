package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TVC_PLANE database table.
 * 
 */
@Entity
@Table(name="TVC_PLANE")
@Getter
@Setter
public class TvcPlane implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TVC_PLANE_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TVC_PLANE_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String tvcPlaneId;

	@Column
	private Integer adults;

	@Column(name="AGENCY_TYPE")
	private String agencyType;

	@Column(name="AREA_ID")
	private String areaId;

	@Column(name="AREA_NAME")
	private String areaName;

	@Column
	private Integer baby;

	@Column(name="BAOVIET_COMPANY_ID")
	private String baovietCompanyId;

	@Column(name="BAOVIET_COMPANY_NAME")
	private String baovietCompanyName;

	@Column(name="BAOVIET_DEPARTMENT_ID")
	private String baovietDepartmentId;

	@Column(name="BAOVIET_DEPARTMENT_NAME")
	private String baovietDepartmentName;

	@Temporal(TemporalType.DATE)
	@Column(name="BV_SYSDATE")
	private Date bvSysdate;

	@Column(name="CHANGE_PREMIUM")
	private Double changePremium;

	@Column
	private Integer children;

	@Column(name="COUPONS_CODE")
	private String couponsCode;

	@Column(name="COUPONS_VALUE")
	private Double couponsValue;

	@Column(name="CUSTOMER_ADDRESS")
	private String customerAddress;

	@Column(name="CUSTOMER_CMT")
	private String customerCmt;

	@Column(name="CUSTOMER_CODE")
	private String customerCode;

	@Column(name="CUSTOMER_EMAIL")
	private String customerEmail;

	@Column(name="CUSTOMER_ID")
	private String customerId;

	@Column(name="CUSTOMER_NAME")
	private String customerName;

	@Column(name="CUSTOMER_PHONE")
	private String customerPhone;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_PAYMENT")
	private Date dateOfPayment;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Temporal(TemporalType.DATE)
	@Column(name="INCEPTION_DATE")
	private Date inceptionDate;

	@Column(name="INVOICE_ADDRESS")
	private String invoiceAddress;

	@Column(name="INVOICE_CHECK")
	private Integer invoiceCheck;

	@Column(name="INVOICE_NAME")
	private String invoiceName;

	@Column(name="INVOICE_TAX")
	private String invoiceTax;

	@Column(name="NET_PREMIUM")
	private Double netPremium;

	@Column
	private String note;

	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;

	@Column(name="PAYMENT_TRANSACTION_ID")
	private String paymentTransactionId;

	@Column(name="PLACE_FROM")
	private String placeFrom;

	@Column(name="PLACE_TO")
	private String placeTo;

	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="PLAN_NAME")
	private String planName;

	@Column(name="POLICY_NUMBER")
	private String policyNumber;

	@Column(name="RECEIVER_ADDRESS")
	private String receiverAddress;

	@Column(name="RECEIVER_EMAIL")
	private String receiverEmail;

	@Column(name="RECEIVER_NAME")
	private String receiverName;

	@Column(name="RECEIVER_PHONE")
	private String receiverPhone;

	@Column(name="SEX_ID")
	private String sexId;

	@Column(name="SEX_NAME")
	private String sexName;

	@Column(name="SO_GYCBH")
	private String soGycbh;

	@Column(name="STATUS_ID")
	private String statusId;

	@Column(name="STATUS_NAME")
	private String statusName;

	@Column(name="TOTAL_PREMIUM")
	private Double totalPremium;

	@Column(name="TRAVEL_WITH_ID")
	private String travelWithId;

	@Column(name="TRAVEL_WITH_NAME")
	private String travelWithName;
}