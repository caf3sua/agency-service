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
 * The persistent class for the PA_RATE database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class HomeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String homeId;

	private String soGycbh = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfRequirement = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String policyNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String policyStatus = AgencyConstants.DEFAULT_STRING_VALUE;

	private String policyStatusName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Integer entitlements = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String insuredLocation = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double totalUsedArea = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Integer yearOfMake = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer windowLocks = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer bars = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer mesh = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer unprotected = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String byDay = AgencyConstants.DEFAULT_STRING_VALUE;

	private String byNight = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double conSi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Integer conPeriodOfCoverage = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer conYearOlds = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Double conRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Integer homePeriodOfCoverage = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String plan = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double planRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double totalNetPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String changePremiumContent = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double changePremiumRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double changePremiumPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double totalBasicPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double premiumVat = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double totalPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String bankId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String bankName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String teamId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String teamName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String agentId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String agentName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private String taxIdNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String postalAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String permanentAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String homePhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String mobilePhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String ownership = AgencyConstants.DEFAULT_STRING_VALUE;

	private String loanAccount = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date smartappSysdate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String pathOwner = AgencyConstants.DEFAULT_STRING_VALUE;

	private String pathCreated = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String baovietCompanyId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietCompanyName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietDepartmentId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietDepartmentName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date sendDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date responseDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String statusRenewalsId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusRenewalsName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String oldPolicyNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String oldGycbhNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String note = AgencyConstants.DEFAULT_STRING_VALUE;

	private String renewalsReason = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double conPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double planPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String baovietUserId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietUserName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String changePremium_type = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double llimitPerItem = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double liabilityPerClaim = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double deductible = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date payment_date = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String invoiceCompany = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoceNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private Integer voiceCheck = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Double feeReceive = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policy_send_date = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String peoplePermanently = AgencyConstants.DEFAULT_STRING_VALUE;

	private String houseMaid = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double liability = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Integer liabilityPeriodOfCoverage = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Double liabilityFee = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String liabilityPlan = AgencyConstants.DEFAULT_STRING_VALUE;

	private Integer dkbs1 = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer dkbs2 = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer dkbs3 = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer dkbs4 = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String provinceId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String provinceName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Integer yearFinish = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer soTang = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer chieuRongNgo = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Integer loaiHinh = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String receiverName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverMoible = AgencyConstants.DEFAULT_STRING_VALUE;

	private String couponsCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double couponsValue = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	private Double si = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double siin = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String yearBuildCode = AgencyConstants.DEFAULT_STRING_VALUE;
	private Double premiumHome = AgencyConstants.DEFAULT_DOUBLE_VALUE;

}