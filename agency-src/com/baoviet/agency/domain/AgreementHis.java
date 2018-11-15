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
 * The persistent class for the AGREEMENT_HIS database table.
 * 
 */
@Entity
@Table(name = "AGREEMENT_HIS")
@Getter
@Setter
public class AgreementHis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "AGREEMENT_HIS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="AGREEMENT_ID", unique=true)
	private String agreementId;
	
	@Column(name="GYCBH_ID")
	private String gycbhId;
	
	@Column(name="GYCBH_NUMBER")
	private String gycbhNumber;
	
	@Column(name="POLICY_NUMBER")
	private String policyNumber;
	
	@Column(name="INCEPTION_DATE")
	private Date inceptionDate;
	
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;
	
	@Column(name="STATUS_GYCBH_ID")
	private String statusGycbhId;

	@Column(name="STATUS_GYCBH_NAME")
	private String statusGycbhName;

	@Column(name="STATUS_POLICY_ID")
	private String statusPolicyId;

	@Column(name="STATUS_POLICY_NAME")
	private String statusPolicyName;
	
	@Column(name="LINE_ID")
	private String lineId;

	@Column(name="LINE_NAME")
	private String lineName;
	
	@Column(name="CONTACT_ID")
	private String contactId;

	@Column(name="CONTACT_NAME")
	private String contactName;
	
	@Column(name="TAX_ID_NUMBER")
	private String taxIdNumber;
	
	@Column(name="CONTACT_ADDRESS")
	private String contactAddress;
	
	@Column(name="CONTACT_PHONE")
	private String contactPhone;
	
	@Column(name="STANDARD_PREMIUM")
	private Double standardPremium;
	
	@Column(name="CHANGE_PREMIUM")
	private Double changePremium;
	
	@Column(name="NET_PREMIUM")
	private Double netPremium;
	
	@Column(name="TOTAL_VAT")
	private Double totalVat;
	
	@Column(name="TOTAL_PREMIUM")
	private Double totalPremium;
	
	@Column
	private Double commision;

	@Column(name="COMMISION_SUPPORT")
	private Double commisionSupport;
	
	@Column(name="SEND_DATE")
	private Date sendDate;
	
	@Column(name="RESPONSE_DATE")
	private Date responseDate;
	
	@Column(name="STATUS_RENEWALS_ID")
	private String statusRenewalsId;
	
	@Column(name="STATUS_RENEWALS_NAME")
	private String statusRenewalsName;
	
	@Column(name="OLD_POLICY_NUMBER")
	private String oldPolicyNumber;
	
	@Column(name="OLD_GYCBH_NUMBER")
	private String oldGycbhNumber;
	
	@Column(name="OLD_GYCBH_ID")
	private String oldGycbhId;
	
	@Column(name="BANK_ID")
	private String bankId;

	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="TEAM_ID")
	private String teamId;

	@Column(name="TEAM_NAME")
	private String teamName;
	
	@Column(name="AGENT_ID")
	private String agentId;

	@Column(name="AGENT_NAME")
	private String agentName;
	
	@Column(name="BAOVIET_COMPANY_ID")
	private String baovietCompanyId;

	@Column(name="BAOVIET_COMPANY_NAME")
	private String baovietCompanyName;

	@Column(name="BAOVIET_DEPARTMENT_ID")
	private String baovietDepartmentId;

	@Column(name="BAOVIET_DEPARTMENT_NAME")
	private String baovietDepartmentName;
	
	@Column(name="AGREEMENT_SYSDATE")
	private Date agreementSysdate;
	
	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="CANCEL_POLICY_DATE")
	private Date cancelPolicyDate;

	@Column(name="CANCEL_POLICY_PREMIUM")
	private Double cancelPolicyPremium;
	
	@Column(name="CANCEL_POLICY_COMMISION")
	private Double cancelPolicyCommision;
	
	@Column(name="DATE_OF_REQUIREMENT")
	private Date dateOfRequirement;
	
	@Column(name="DATE_OF_PAYMENT")
	private Date dateOfPayment;
	
	@Column(name="MCI_ADD_ID")
	private String mciAddId;
	
	@Column(name="IS_POLICY")
	private Integer isPolicy;
	
	@Column(name="REASON_CANCEL")
	private String reasonCancel;
	
	@Column(name="OLD_POLICY_STATUS_ID")
	private String oldPolicyStatusId;

	@Column(name="OLD_POLICY_STATUS_NAME")
	private String oldPolicyStatusName;
	
	@Column(name="CLAIM_RATE")
	private Double claimRate;
	
	@Column(name="RENEWALS_REASON")
	private String renewalsReason;
	
	@Column(name="RENEWALS_RATE")
	private Double renewalsRate;
	
	@Column(name="RENEWALS_PREMIUM")
	private Double renewalsPremium;
	
	@Column(name="CLAIM_RATE1")
	private Double claimRate1;

	@Column(name="CLAIM_RATE2")
	private Double claimRate2;
	
	@Column(name="STATUS_RENEWALS_ID1")
	private String statusRenewalsId1;
	
	@Column(name="STATUS_RENEWALS_NAME1")
	private String statusRenewalsName1;
	
	@Column(name="RENEWALS_RATE1")
	private Double renewalsRate1;
	
	@Column(name="RENEWALS_PREMIUM1")
	private Double renewalsPremium1;
	
	@Column(name="RENEWALS_DES1")
	private String renewalsDes1;
	
	@Column(name="STATUS_RENEWALS_ID2")
	private String statusRenewalsId2;
	
	@Column(name="STATUS_RENEWALS_NAME2")
	private String statusRenewalsName2;
	
	@Column(name="RENEWALS_RATE2")
	private Double renewalsRate2;
	
	@Column(name="RENEWALS_PREMIUM2")
	private Double renewalsPremium2;
	
	@Column(name="RENEWALS_DES2")
	private String renewalsDes2;
	
	@Column(name="TYPE_OF_PRINT")
	private String typeOfPrint;
	
	@Column(name="RENEWALS_CHOICE")
	private String renewalsChoice;
	
	@Column(name="RENEWALS_SI")
	private Double renewalsSi;
	
	@Column(name="CANCEL_POLICY_SUPPORT")
	private Double cancelPolicySupport;
	
	@Column(name="CANCEL_POLICY_PREMIUM2")
	private Double cancelPolicyPremium2;
	
	@Column(name="CANCEL_POLICY_COMMISION2")
	private Double cancelPolicyCommision2;
	
	@Column(name="CANCEL_POLICY_SUPPORT2")
	private Double cancelPolicySupport2;
	
	@Column(name="CANCEL_POLICY_PREMIUM3")
	private Double cancelPolicyPremium3;
	
	@Column(name="CANCEL_POLICY_COMMISION3")
	private Double cancelPolicyCommision3;
	
	@Column(name="CANCEL_POLICY_SUPPORT3")
	private Double cancelPolicySupport3;
	
	@Column(name="FEE_RECEIVE")
	private Double feeReceive;
	
	@Column(name="POLICY_SEND_DATE")
	private Date policySendDate;
	
	@Column(name="CONTACT_DOB")
	private Date contactDob;
	
	@Column(name="CONTACT_USERNAME")
	private String contactUsername;
	
	@Column(name="CONTACT_ADDRESSTT")
	private String contactAddresstt;
	
	@Column(name="RECEIVER_NAME")
	private String receiverName;
	
	@Column(name="RECEIVER_ADDRESS")
	private String receiverAddress;
	
	@Column(name="RECEIVER_EMAIL")
	private String receiverEmail;

	@Column(name="RECEIVER_MOIBLE")
	private String receiverMoible;
	
	@Column(name="COUPONS_CODE")
	private String couponsCode;

	@Column(name="COUPONS_VALUE")
	private Double couponsValue;
	
	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name="RECEIVE_METHOD")
	private String receiveMethod;
	
	@Column(name="PARENT_ID")
	private String parentId;
	
	@Column(name="PAY_DISCOUNT")
	private Double payDiscount;
	
	@Column(name="INVOICE_BUYER")
	private String invoiceBuyer;

	@Column(name="INVOICE_COMPANY")
	private String invoiceCompany;

	@Column(name="INVOICE_TAX_NO")
	private String invoiceTaxNo;
	
	@Column(name="INVOICE_ADDRESS")
	private String invoiceAddress;
	
	@Column(name="INVOICE_ACCOUNT_NO")
	private String invoiceAccountNo;
	
	@Column(name="USER_AGENT")
	private String userAgent;
	
	@Column(name="PAYMENT_GATEWAY")
	private String paymentGateway;
	
	@Column(name="CREATE_DATE")
	private Date createDate;
}