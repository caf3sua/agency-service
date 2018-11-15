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
 * The persistent class for the TL database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TlDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tlId;

	private String agentId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String agentName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String bankId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String bankName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietCompanyId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietCompanyName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietDepartmentId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietDepartmentName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietUserId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietUserName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryIdNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryRelationship = AgencyConstants.DEFAULT_STRING_VALUE;

	private String changePremiumContent = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double changePremiumPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double changePremiumRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String changePremiumType = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactGoitinhId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactGoitinhName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactMobilePhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactPhone = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfBirth = AgencyConstants.DEFAULT_DOB_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfPayment = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfRequirement = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private Double death = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private Double feeReceive = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String invoceNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceCompany = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double medicalExpenses = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String note = AgencyConstants.DEFAULT_STRING_VALUE;

	private String occupation = AgencyConstants.DEFAULT_STRING_VALUE;

	private String oldGycbhNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String oldPolicyNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String pathCreated = AgencyConstants.DEFAULT_STRING_VALUE;

	private String pathOwner = AgencyConstants.DEFAULT_STRING_VALUE;

	private String permanentAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double permanentPartDisablement = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Integer permanentTotalDisablement = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String plan = AgencyConstants.DEFAULT_STRING_VALUE;

	private String policyNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policySendDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String policyStatus = AgencyConstants.DEFAULT_STRING_VALUE;

	private String policyStatusName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String postalAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverMobile = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String renewalsReason = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date responseDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date sendDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date smartappSysdate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String soGycbh = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusRenewalsId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusRenewalsName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String taxIdNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String teamId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String teamName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double totalBasicPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double totalNetPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double totalPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Integer voiceCheck = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Double weeklyBenefit = AgencyConstants.DEFAULT_DOUBLE_VALUE;

}