package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the KCARE database table.
 * 
 */
@Entity
@Getter
@Setter
public class Kcare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "KCARE_SEQ") })
//	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
//        	@Parameter(name = "sequence_name", value = "KCARE_SEQ")
//        }
//    )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="K_ID", unique=true)
	private String kId;

	@Column(name="AGENT_ID")
	private String agentId;

	@Column(name="AGENT_NAME")
	private String agentName;

	@Column(name="BANK_CONFIRM")
	private String bankConfirm;

	@Column(name="BANK_ID")
	private String bankId;

	@Column(name="BANK_NAME")
	private String bankName;

	@Column(name="BANK_NOTE")
	private String bankNote;

	@Column(name="BAOVIET_CONFIRM")
	private String baovietConfirm;

	@Column(name="BAOVIET_DEPARTMENT_ID")
	private String baovietDepartmentId;

	@Column(name="BAOVIET_DEPARTMENT_NAME")
	private String baovietDepartmentName;

	@Column(name="BAOVIET_ID")
	private String baovietId;

	@Column(name="BAOVIET_NAME")
	private String baovietName;

	@Column(name="BAOVIET_NOTE")
	private String baovietNote;

	@Column(name="BENEFICIARY_ADDRESS")
	private String beneficiaryAddress;

	@Column(name="BENEFICIARY_ADDRESS_D")
	private String beneficiaryAddressD;

	@Temporal(TemporalType.DATE)
	@Column(name="BENEFICIARY_DOB")
	private Date beneficiaryDob;

	@Temporal(TemporalType.DATE)
	@Column(name="BENEFICIARY_DOB_D")
	private Date beneficiaryDobD;

	@Column(name="BENEFICIARY_EMAIL")
	private String beneficiaryEmail;

	@Column(name="BENEFICIARY_EMAIL_D")
	private String beneficiaryEmailD;

	@Column(name="BENEFICIARY_ID_NUMBER")
	private String beneficiaryIdNumber;

	@Column(name="BENEFICIARY_ID_NUMBER_D")
	private String beneficiaryIdNumberD;

	@Column(name="BENEFICIARY_NAME")
	private String beneficiaryName;

	@Column(name="BENEFICIARY_NAME_D")
	private String beneficiaryNameD;

	@Column(name="BENEFICIARY_PHONE")
	private String beneficiaryPhone;

	@Column(name="BENEFICIARY_PHONE_D")
	private String beneficiaryPhoneD;

	@Column(name="BENEFICIARY_RELATIONSHIP")
	private String beneficiaryRelationship;

	@Column(name="BENEFICIARY_RELATIONSHIP_D")
	private String beneficiaryRelationshipD;

	@Column(name="CHANGE_PREMIUM")
	private Double changePremium;

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

	@Column(name="COUPONS_CODE")
	private String couponsCode;

	@Column(name="COUPONS_VALUE")
	private Double couponsValue;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_PAYMENT")
	private Date dateOfPayment;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column(name="FEE_RECEIVE")
	private Double feeReceive;

	@Temporal(TemporalType.DATE)
	@Column(name="INCEPTION_DATE")
	private Date inceptionDate;

	@Column(name="INSURED_ADDRESS")
	private String insuredAddress;

	@Column(name="INSURED_DOB")
	private String insuredDob;

	@Column(name="INSURED_ID_NUMBER")
	private String insuredIdNumber;

	@Column(name="INSURED_NAME")
	private String insuredName;

	@Column(name="INSURED_OCCUPATION")
	private String insuredOccupation;

	@Column(name="INSURED_SEX")
	private String insuredSex;

	@Column(name="INVOICE_ACCOUNT_NO")
	private String invoiceAccountNo;

	@Column(name="INVOICE_ADDRESS")
	private String invoiceAddress;

	@Column(name="INVOICE_BUYER")
	private String invoiceBuyer;

	@Column(name="INVOICE_CHECK")
	private Integer invoiceCheck;

	@Column(name="INVOICE_COMPANY")
	private String invoiceCompany;

	@Column(name="INVOICE_TAX_NO")
	private String invoiceTaxNo;

	@Column(name="MESSAGE_ID")
	private Double messageId;

	@Column(name="NET_PREMIUM")
	private Double netPremium;

	@Column
	private String note;

	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;

	@Column(name="PERMANENT_ADDRESS")
	private String permanentAddress;

	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="PLAN_NAME")
	private String planName;

	@Temporal(TemporalType.DATE)
	@Column(name="POLICY_DELIVER")
	private Date policyDeliver;

	@Column(name="POLICY_NUMBER")
	private String policyNumber;

	@Column(name="POSTAL_ADDRESS")
	private String postalAddress;

	@Temporal(TemporalType.DATE)
	@Column(name="PROPSER_NGAYSINH")
	private Date propserNgaysinh;

	@Column(name="PROPSER_TITLE")
	private String propserTitle;

	@Temporal(TemporalType.DATE)
	@Column(name="Q_DAY_TREATMENT")
	private Date qDayTreatment;

	@Column(name="Q_DIAGNOSE")
	private String qDiagnose;

	@Column(name="Q_HOPITAL")
	private String qHopital;

	@Column(name="Q_PATIENT")
	private String qPatient;

	@Column(name="Q_RELATIONSHIP")
	private String qRelationship;

	@Column(name="Q_RESULT_CAN")
	private String qResultCan;

	@Column(name="Q_RESULT_TRE")
	private String qResultTre;

	@Column(name="Q_TREATMENT")
	private String qTreatment;

	@Column(name="Q_TYPE_CANCER")
	private String qTypeCancer;

	@Column
	private String q1;

	@Column
	private String q10;

	@Column
	private String q2;

	@Column
	private String q3;

	@Column
	private String q4;

	@Column
	private String q5;

	@Column
	private String q6;

	@Column
	private String q7;

	@Column
	private String q8;

	@Column
	private String q9;

	@Column(name="RECEIVER_ADDRESS")
	private String receiverAddress;

	@Column(name="RECEIVER_EMAIL")
	private String receiverEmail;

	@Column(name="RECEIVER_MOIBLE")
	private String receiverMoible;

	@Column(name="RECEIVER_NAME")
	private String receiverName;

	@Temporal(TemporalType.DATE)
	@Column(name="REQ_DATE")
	private Date reqDate;

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

	@Column(name="STATUS_POLICY_ID")
	private String statusPolicyId;

	@Column(name="STATUS_POLICY_NAME")
	private String statusPolicyName;

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

	@Column(name="TRAVEL_WITH_ID")
	private String travelWithId;

	@Column(name="TRAVEL_WITH_NAME")
	private String travelWithName;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;
}