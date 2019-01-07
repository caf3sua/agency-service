package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGREEMENT database table.
 * 
 */
@Entity
@Table(name = "AGREEMENT")
@Getter
@Setter
public class Agreement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "AGREEMENT_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="AGREEMENT_ID", unique=true)
	private String agreementId;
	
	@Column(name="AGENCY_ID")
	private String agencyId;

	@Column(name="AGENCY_NAME")
	private String agencyName;

	@Column(name="AGENCY_P1_ID")
	private String agencyP1Id;

	@Column(name="AGENCY_P1_NAME")
	private String agencyP1Name;

	@Column(name="AGENCY_P2_ID")
	private String agencyP2Id;

	@Column(name="AGENCY_P2_NAME")
	private String agencyP2Name;

	@Column(name="AGENCY_P3_ID")
	private String agencyP3Id;

	@Column(name="AGENCY_P3_NAME")
	private String agencyP3Name;

	@Column(name="AGENCY_P4_ID")
	private String agencyP4Id;

	@Column(name="AGENCY_P4_NAME")
	private String agencyP4Name;

	@Column(name="AGENCY_P5_ID")
	private String agencyP5Id;

	@Column(name="AGENCY_P5_NAME")
	private String agencyP5Name;

	@Column(name="AGENCY_P6_ID")
	private String agencyP6Id;

	@Column(name="AGENCY_P6_NAME")
	private String agencyP6Name;

	@Column(name="AGENT_ID")
	private String agentId;

	@Column(name="AGENT_NAME")
	private String agentName;

	@Column(name="AGREEMENT_SYSDATE")
	private Date agreementSysdate;

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

	@Column(name="CANCEL_POLICY_COMMISION")
	private Double cancelPolicyCommision;

	@Column(name="CANCEL_POLICY_COMMISION2")
	private Double cancelPolicyCommision2;

	@Column(name="CANCEL_POLICY_COMMISION3")
	private Double cancelPolicyCommision3;

	@Column(name="CANCEL_POLICY_DATE")
	private Date cancelPolicyDate;

	@Column(name="CANCEL_POLICY_PREMIUM")
	private Double cancelPolicyPremium;

	@Column(name="CANCEL_POLICY_PREMIUM2")
	private Double cancelPolicyPremium2;

	@Column(name="CANCEL_POLICY_PREMIUM3")
	private Double cancelPolicyPremium3;

	@Column(name="CANCEL_POLICY_SUPPORT")
	private Double cancelPolicySupport;

	@Column(name="CANCEL_POLICY_SUPPORT2")
	private Double cancelPolicySupport2;

	@Column(name="CANCEL_POLICY_SUPPORT3")
	private Double cancelPolicySupport3;

	@Column(name="CHANGE_PREMIUM")
	private Double changePremium;

	@Column(name="CLAIM_RATE")
	private Double claimRate;

	@Column(name="CLAIM_RATE1")
	private Double claimRate1;

	@Column(name="CLAIM_RATE2")
	private Double claimRate2;

	@Column
	private Double commision;

	@Column(name="COMMISION_SUPPORT")
	private Double commisionSupport;

	@Column(name="CONTACT_ADDRESS")
	private String contactAddress;

	@Column(name="CONTACT_ADDRESSTT")
	private String contactAddresstt;

	@Column(name="CONTACT_DOB")
	private Date contactDob;

	@Column(name="CONTACT_ID")
	private String contactId;

	@Column(name="CONTACT_NAME")
	private String contactName;

	@Column(name="CONTACT_PHONE")
	private String contactPhone;

	@Column(name="CONTACT_USERNAME")
	private String contactUsername;

	@Column(name="COUPONS_CODE")
	private String couponsCode;

	@Column(name="COUPONS_VALUE")
	private Double couponsValue;

	@Column(name="DATE_OF_PAYMENT")
	private Date dateOfPayment;

	@Column(name="DATE_OF_REQUIREMENT")
	private Date dateOfRequirement;

	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column(name="FEE_RECEIVE")
	private Double feeReceive;

	@Column(name="GYCBH_ID")
	private String gycbhId;

	@Column(name="GYCBH_NUMBER")
	private String gycbhNumber;

	@Column(name="INCEPTION_DATE")
	private Date inceptionDate;

	@Column(name="INVOICE_ACCOUNT_NO")
	private String invoiceAccountNo;

	@Column(name="INVOICE_ADDRESS")
	private String invoiceAddress;

	@Column(name="INVOICE_BUYER")
	private String invoiceBuyer;

	@Column(name="INVOICE_COMPANY")
	private String invoiceCompany;

	@Column(name="INVOICE_TAX_NO")
	private String invoiceTaxNo;

	@Column(name="IS_POLICY")
	private Integer isPolicy;

	@Column(name="LINE_ID")
	private String lineId;

	@Column(name="LINE_NAME")
	private String lineName;

	@Column(name="MCI_ADD_ID")
	private String mciAddId;

	@Column(name="NET_PREMIUM")
	private Double netPremium;

	@Column(name="OLD_GYCBH_ID")
	private String oldGycbhId;

	@Column(name="OLD_GYCBH_NUMBER")
	private String oldGycbhNumber;

	@Column(name="OLD_POLICY_NUMBER")
	private String oldPolicyNumber;

	@Column(name="OLD_POLICY_STATUS_ID")
	private String oldPolicyStatusId;

	@Column(name="OLD_POLICY_STATUS_NAME")
	private String oldPolicyStatusName;

	@Column(name="PAY_DISCOUNT")
	private Double payDiscount;

	@Column(name="PAYMENT_GATEWAY")
	private String paymentGateway;

	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;

	@Column(name="PAYMENT_TRANSACTION_ID")
	private String paymentTransactionId;

	@Column(name="POLICY_NUMBER")
	private String policyNumber;

	@Column(name="POLICY_SEND_DATE")
	private Date policySendDate;

	@Column(name="REASON_CANCEL")
	private String reasonCancel;

	@Column(name="RECEIVE_METHOD")
	private String receiveMethod;

	@Column(name="RECEIVER_ADDRESS")
	private String receiverAddress;

	@Column(name="RECEIVER_EMAIL")
	private String receiverEmail;

	@Column(name="RECEIVER_MOIBLE")
	private String receiverMoible;

	@Column(name="RECEIVER_NAME")
	private String receiverName;

	@Column(name="RENEWALS_CHOICE")
	private String renewalsChoice;

	@Column(name="RENEWALS_DES1")
	private String renewalsDes1;

	@Column(name="RENEWALS_DES2")
	private String renewalsDes2;

	@Column(name="RENEWALS_PREMIUM")
	private Double renewalsPremium;

	@Column(name="RENEWALS_PREMIUM1")
	private Double renewalsPremium1;

	@Column(name="RENEWALS_PREMIUM2")
	private Double renewalsPremium2;

	@Column(name="RENEWALS_RATE")
	private Double renewalsRate;

	@Column(name="RENEWALS_RATE1")
	private Double renewalsRate1;

	@Column(name="RENEWALS_RATE2")
	private Double renewalsRate2;

	@Column(name="RENEWALS_REASON")
	private String renewalsReason;

	@Column(name="RENEWALS_SI")
	private Double renewalsSi;

	@Column(name="RESPONSE_DATE")
	private Date responseDate;

	@Column(name="SEND_DATE")
	private Date sendDate;

	@Column(name="STANDARD_PREMIUM")
	private Double standardPremium;

	@Column(name="STATUS_GYCBH_ID")
	private String statusGycbhId;

	@Column(name="STATUS_GYCBH_NAME")
	private String statusGycbhName;

	@Column(name="STATUS_POLICY_ID")
	private String statusPolicyId;

	@Column(name="STATUS_POLICY_NAME")
	private String statusPolicyName;

	@Column(name="STATUS_RENEWALS_ID")
	private String statusRenewalsId;

	@Column(name="STATUS_RENEWALS_ID1")
	private String statusRenewalsId1;

	@Column(name="STATUS_RENEWALS_ID2")
	private String statusRenewalsId2;

	@Column(name="STATUS_RENEWALS_NAME")
	private String statusRenewalsName;

	@Column(name="STATUS_RENEWALS_NAME1")
	private String statusRenewalsName1;

	@Column(name="STATUS_RENEWALS_NAME2")
	private String statusRenewalsName2;

	@Column
	private String subject;

	@Column(name="TAX_ID_NUMBER")
	private String taxIdNumber;

	@Column(name="TEAM_ID")
	private String teamId;

	@Column(name="TEAM_NAME")
	private String teamName;

	@Column(name="TOTAL_PREMIUM")
	private Double totalPremium;

	@Column(name="TOTAL_VAT")
	private Double totalVat;

	@Column(name="TYPE_OF_PRINT")
	private String typeOfPrint;

	@Column(name="USER_AGENT")
	private String userAgent;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;
	
	@Column
	private String otp;
	
	@Column
	private String otpStatus;
	
	@Column
	private String otpStartTime;
	
	@Column
	private String discount;
	
	@Column
	private Integer createType;
	
	@Column
	private String urnContact;
	
	@Column
	private String urnDaily;
	
	@Column
	private String urnDepartmentId;
	
	@Column
	private Integer sendSms;
	
	@Column
	private Integer sendEmail;
	
	@Column
	private Integer sendWorkplace;
	
	@Column
	private Integer sendEmailOtp;
}