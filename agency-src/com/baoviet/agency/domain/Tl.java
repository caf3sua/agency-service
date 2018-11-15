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
 * The persistent class for the TL database table.
 * 
 */
@Entity
@Table(name="TL")
@Getter
@Setter
public class Tl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TL_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TL_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String tlId;

	@Column(name="AGENT_ID")
	private String agentId;

	@Column(name="AGENT_NAME")
	private String agentName;

	@Column(name="BANK_ID")
	private String bankId;

	@Column(name="BANK_NAME")
	private String bankName;

	@Column(name="BAOVIET_COMPANY_ID")
	private String baovietCompanyId;

	@Column(name="BAOVIET_COMPANY_NAME")
	private String baovietCompanyName;

	@Column(name="BAOVIET_DEPARTMENT_ID")
	private String baovietDepartmentId;

	@Column(name="BAOVIET_DEPARTMENT_NAME")
	private String baovietDepartmentName;

	@Column(name="BAOVIET_USER_ID")
	private String baovietUserId;

	@Column(name="BAOVIET_USER_NAME")
	private String baovietUserName;

	@Column(name="BENEFICIARY_ADDRESS")
	private String beneficiaryAddress;

	@Column(name="BENEFICIARY_ID_NUMBER")
	private String beneficiaryIdNumber;

	@Column(name="BENEFICIARY_NAME")
	private String beneficiaryName;

	@Column(name="BENEFICIARY_RELATIONSHIP")
	private String beneficiaryRelationship;

	@Column(name="CHANGE_PREMIUM_CONTENT")
	private String changePremiumContent;

	@Column(name="CHANGE_PREMIUM_PREMIUM")
	private Double changePremiumPremium;

	@Column(name="CHANGE_PREMIUM_RATE")
	private Double changePremiumRate;

	@Column(name="CHANGE_PREMIUM_TYPE")
	private String changePremiumType;

	@Column(name="CONTACT_CODE")
	private String contactCode;

	@Column(name="CONTACT_EMAIL")
	private String contactEmail;

	@Column(name="CONTACT_GOITINH_ID")
	private String contactGoitinhId;

	@Column(name="CONTACT_GOITINH_NAME")
	private String contactGoitinhName;

	@Column(name="CONTACT_ID")
	private String contactId;

	@Column(name="CONTACT_MOBILE_PHONE")
	private String contactMobilePhone;

	@Column(name="CONTACT_NAME")
	private String contactName;

	@Column(name="CONTACT_PHONE")
	private String contactPhone;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_PAYMENT")
	private Date dateOfPayment;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_REQUIREMENT")
	private Date dateOfRequirement;

	@Column
	private Double death;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column(name="FEE_RECEIVE")
	private Double feeReceive;

	@Temporal(TemporalType.DATE)
	@Column(name="INCEPTION_DATE")
	private Date inceptionDate;

	@Column(name="INVOCE_NUMBER")
	private String invoceNumber;

	@Column(name="INVOICE_ADDRESS")
	private String invoiceAddress;

	@Column(name="INVOICE_COMPANY")
	private String invoiceCompany;

	@Column(name="MEDICAL_EXPENSES")
	private Double medicalExpenses;

	@Column
	private String note;

	@Column
	private String occupation;

	@Column(name="OLD_GYCBH_NUMBER")
	private String oldGycbhNumber;

	@Column(name="OLD_POLICY_NUMBER")
	private String oldPolicyNumber;

	@Column(name="PATH_CREATED")
	private String pathCreated;

	@Column(name="PATH_OWNER")
	private String pathOwner;

	@Column(name="PERMANENT_ADDRESS")
	private String permanentAddress;

	@Column(name="PERMANENT_PART_DISABLEMENT")
	private Double permanentPartDisablement;

	@Column(name="PERMANENT_TOTAL_DISABLEMENT")
	private Integer permanentTotalDisablement;

	@Column
	private String plan;

	@Column(name="POLICY_NUMBER")
	private String policyNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="POLICY_SEND_DATE")
	private Date policySendDate;

	@Column(name="POLICY_STATUS")
	private String policyStatus;

	@Column(name="POLICY_STATUS_NAME")
	private String policyStatusName;

	@Column(name="POSTAL_ADDRESS")
	private String postalAddress;

	@Column(name="RECEIVER_ADDRESS")
	private String receiverAddress;

	@Column(name="RECEIVER_MOBILE")
	private String receiverMobile;

	@Column(name="RECEIVER_NAME")
	private String receiverName;

	@Column(name="RENEWALS_REASON")
	private String renewalsReason;

	@Temporal(TemporalType.DATE)
	@Column(name="RESPONSE_DATE")
	private Date responseDate;

	@Temporal(TemporalType.DATE)
	@Column(name="SEND_DATE")
	private Date sendDate;

	@Temporal(TemporalType.DATE)
	@Column(name="SMARTAPP_SYSDATE")
	private Date smartappSysdate;

	@Column(name="SO_GYCBH")
	private String soGycbh;

	@Column(name="STATUS_ID")
	private String statusId;

	@Column(name="STATUS_NAME")
	private String statusName;

	@Column(name="STATUS_RENEWALS_ID")
	private String statusRenewalsId;

	@Column(name="STATUS_RENEWALS_NAME")
	private String statusRenewalsName;

	@Column(name="TAX_ID_NUMBER")
	private String taxIdNumber;

	@Column(name="TEAM_ID")
	private String teamId;

	@Column(name="TEAM_NAME")
	private String teamName;

	@Column(name="TOTAL_BASIC_PREMIUM")
	private Double totalBasicPremium;

	@Column(name="TOTAL_NET_PREMIUM")
	private Double totalNetPremium;

	@Column(name="TOTAL_PREMIUM")
	private Double totalPremium;

	@Column(name="VOICE_CHECK")
	private Integer voiceCheck;

	@Column(name="WEEKLY_BENEFIT")
	private Double weeklyBenefit;

}