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

import com.baoviet.agency.config.AgencyConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the PA_RATE database table.
 * 
 */
@Entity
@Table(name = "CARS")
@Getter
@Setter
public class Car implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = {
			@Parameter(name = "sequence", value = "CARS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name = "CAR_ID", unique = true)
	private String carId;
	@Column
	private String soGycbh;
	@Column
	private String statusId;
	@Column
	private String statusName;
	@Column
	private Date dateOfRequirement;
	@Column
	private Date inceptionDate;
	@Column
	private Date expiredDate;
	@Column
	private Date dateOfPayment;
	@Column
	private String policyNumber;
	@Column
	private String policyStatus;
	@Column
	private String policyStatusName;
	@Column
	private Double entitlements;
	@Column
	private String registrationNumber;
	@Column
	private String chassisNumber;
	@Column
	private String engineNumber;
	@Column
	private String origin;
	@Column
	private String originName;
	@Column
	private Double actualValue;
	@Column
	private String makeId;
	@Column
	private String makeName;
	@Column
	private String modelId;
	@Column
	private String modelName;
	@Column
	private String typeOfCarid;
	@Column
	private String typeOfCarname;
	@Column
	private Double seatNumber;
	@Column
	private String yearOfMake;
	@Column
	private String yearOfUse;
	@Column
	private String purposeOfUsageId;
	@Column
	private String purposeOfUsageName;
	@Column
	private Double physicalDamageSi;
	@Column
	private Double physicalDamageRate;
	@Column
	private Double physicalDamagePremium;
	@Column
	private Double thirdPartySiTs;
	@Column
	private Double thirdPartySiCn;
	@Column
	private Double thirdPartyRate;
	@Column
	private Double thirdPartyPremium;
	@Column
	private Double passengersAccidentSi;
	@Column
	private Double passengersAccidentRate;
	@Column
	private Double passengersAccidentPremium;
	@Column
	private Double passengersAccidentPerson;
	@Column
	private Double passengersAccidentNumber;
	@Column
	private Double matCapBoPhanRate;
	@Column
	private Double matCapBoPhanPremium;
	@Column
	private Double totalNetPremium;
	@Column
	private String changePremiumId;
	@Column
	private String changePremiumContent;
	@Column
	private Double changePremiumRate;
	@Column
	private Double changePremiumPremium;
	@Column
	private Double totalBasicPremium;
	@Column
	private Double premiumVat;
	@Column
	private Double totalPremium;
	@Column
	private String bankId;
	@Column
	private String bankName;
	@Column
	private String teamId;
	@Column
	private String teamName;
	@Column
	private String agentId;
	@Column
	private String agentName;
	@Column
	private String contactId;
	@Column
	private String contactCode;
	@Column
	private String taxIdNumber;
	@Column
	private String postalAddress;
	@Column
	private String permanentAddress;
	@Column
	private String contactName;
	@Column
	private Date dateOfBirth;
	@Column
	private String occupation;
	@Column
	private String contactPhone;
	@Column
	private String contactMobilePhone;
	@Column
	private String contactEmail;
	@Column
	private String contactGioitinhId;
	@Column
	private String contactGioitinhName;
	@Column
	private Date smartappSysdate;
	@Column
	private String pathOwner;
	@Column
	private String pathCreated;
	@Column
	private String baovietUserId;
	@Column
	private String baovietUserName;
	@Column
	private String baovietCompanyId;
	@Column
	private String baovietCompanyName;
	@Column
	private String baovietDepartmentId;
	@Column
	private String baovietDepartmentName;
	@Column
	private Date sendDate;
	@Column
	private Date responseDate;
	@Column
	private String statusRenewalsId;
	@Column
	private String statusRenewalsName;
	@Column
	private Double renewalsRate;
	@Column
	private Double renewalsPremium;
	@Column
	private String oldPolicyNumber;
	@Column
	private String oldGycbhNumber;
	@Column
	private String note;
	@Column
	private String renewalsReason;
	@Column
	private String loanAccount;
	@Column
	private String changePremiumPaId;
	@Column
	private String changePremiumPaContent;
	@Column
	private Double changePremiumPaRate;
	@Column
	private Double changePremiumPaPremium;
	@Column
	private String invoiceCompany;
	@Column
	private String invoiceAddress;
	@Column
	private String invoiceNumber;
	@Column
	private Double invoiceCheck;
	@Column
	private Double feeReceive;
	@Column
	private Date policySendDate;
	@Column
	private String receiverName;
	@Column
	private String receiverAddress;
	@Column
	private String receiverEmail;
	@Column
	private String receiverMoible;
	@Column
	private String couponsCode;
	@Column
	private Double couponsValue;
	@Column
	private Double khauTru;
	@Column
	private Double khaoHao;
	@Column
	private Double matCap;
	@Column
	private Double ngapNuoc;
	@Column
	private Double garage;
	@Column
	private String nganhangTh;
	@Column
	private String chinhanhTh;
	@Column
	private String diachiTh;
	@Column
	private Double sotienTh;
	@Column
	private String monthOfMake;
	@Column
	private String categoryType;
	@Column
	private String packageType;
}