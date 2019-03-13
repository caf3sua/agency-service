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
 * The persistent class for the MOTO database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class CarDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String carId;

	private String soGycbh = AgencyConstants.DEFAULT_STRING_VALUE;
	private String statusId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String statusName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfRequirement = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfPayment = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	private Double entitlements = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String origin = AgencyConstants.DEFAULT_STRING_VALUE;
	private String originName = AgencyConstants.DEFAULT_STRING_VALUE;
	private String typeOfCarname = AgencyConstants.DEFAULT_STRING_VALUE;
	private Double seatNumber = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String yearOfUse = AgencyConstants.DEFAULT_STRING_VALUE;

	// nhom loai xe: 15: xe khong kinh doanh van tai
	private String purposeOfUsageName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String typeOfCarid = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double thirdPartySiTs = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double thirdPartySiCn = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double thirdPartyRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	// số tiền tham gia bảo hiểm trách nhiệm dân sự tự nguyện
	private Double matCapBoPhanRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	// phí cần đóng tham gia bh trách nhiệm dân sự tự nguyện
	private Double matCapBoPhanPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double khauTru = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double khaoHao = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double matCap = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double ngapNuoc = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double garage = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double passengersAccidentRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double passengersAccidentPerson = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double physicalDamageRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String policyStatus = AgencyConstants.DEFAULT_STRING_VALUE;
	private String policyStatusName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double totalNetPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String changePremiumId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String changePremiumContent = AgencyConstants.DEFAULT_STRING_VALUE;
	private Double changePremiumRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double changePremiumPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double totalBasicPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double premiumVat = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String bankId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String bankName = AgencyConstants.DEFAULT_STRING_VALUE;
	private String teamId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String teamName = AgencyConstants.DEFAULT_STRING_VALUE;
	private String agentId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String agentName = AgencyConstants.DEFAULT_STRING_VALUE;
	private String taxIdNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	private String postalAddress = AgencyConstants.DEFAULT_STRING_VALUE;
	private String permanentAddress = AgencyConstants.DEFAULT_STRING_VALUE;
	private String contactName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfBirth = AgencyConstants.DEFAULT_DOB_VALUE;
	private String occupation = AgencyConstants.DEFAULT_STRING_VALUE;
	private String contactPhone = AgencyConstants.DEFAULT_STRING_VALUE;
	private String contactMobilePhone = AgencyConstants.DEFAULT_STRING_VALUE;
	private String contactEmail = AgencyConstants.DEFAULT_STRING_VALUE;
	private String contactGioitinhId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String contactGioitinhName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date smartappSysdate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	private String pathOwner = AgencyConstants.DEFAULT_STRING_VALUE;
	private String pathCreated = AgencyConstants.DEFAULT_STRING_VALUE;
	private String baovietUserId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String baovietUserName = AgencyConstants.DEFAULT_STRING_VALUE;
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
	private Double renewalsRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double renewalsPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String oldPolicyNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	private String oldGycbhNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	private String note = AgencyConstants.DEFAULT_STRING_VALUE;
	private String renewalsReason = AgencyConstants.DEFAULT_STRING_VALUE;
	private String loanAccount = AgencyConstants.DEFAULT_STRING_VALUE;
	private String changePremiumPaId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String changePremiumPaContent = AgencyConstants.DEFAULT_STRING_VALUE;
	private Double changePremiumPaRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double changePremiumPaPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double feeReceive = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policySendDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String couponsCode = AgencyConstants.DEFAULT_STRING_VALUE;
	private Double couponsValue = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String nganhangTh = AgencyConstants.DEFAULT_STRING_VALUE;
	private String chinhanhTh = AgencyConstants.DEFAULT_STRING_VALUE;
	private String diachiTh = AgencyConstants.DEFAULT_STRING_VALUE;
	private Double sotienTh = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String contactId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String contactCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double actualValue = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double changePremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String chassisNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	private String engineNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	private Boolean garageCheck = AgencyConstants.DEFAULT_BOOLEAN_VALUE;
	private String insuredAddress = AgencyConstants.DEFAULT_STRING_VALUE;
	private String insuredName = AgencyConstants.DEFAULT_STRING_VALUE;
	private String invoiceAddress = AgencyConstants.DEFAULT_STRING_VALUE;
	private String invoiceCompany = AgencyConstants.DEFAULT_STRING_VALUE;
	private Double invoiceCheck = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String invoiceNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	private Boolean khaoHaoCheck = AgencyConstants.DEFAULT_BOOLEAN_VALUE;
	private Boolean khauTruCheck = AgencyConstants.DEFAULT_BOOLEAN_VALUE;
	private String makeId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String makeName = AgencyConstants.DEFAULT_STRING_VALUE;
	private Boolean matCapCheck = AgencyConstants.DEFAULT_BOOLEAN_VALUE;
	private String modelId = AgencyConstants.DEFAULT_STRING_VALUE;
	private String modelName = AgencyConstants.DEFAULT_STRING_VALUE;
	private Boolean nntxCheck = AgencyConstants.DEFAULT_BOOLEAN_VALUE;
	private Boolean ngapNuocCheck = AgencyConstants.DEFAULT_BOOLEAN_VALUE;
	private Double passengersAccidentNumber = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double passengersAccidentPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double passengersAccidentSi = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String policyNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	private Double premium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String purposeOfUsageId = AgencyConstants.DEFAULT_STRING_VALUE;
	private Double physicalDamagePremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double physicalDamageSi = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String receiverAddress = AgencyConstants.DEFAULT_STRING_VALUE;
	private String receiverEmail = AgencyConstants.DEFAULT_STRING_VALUE;
	private String receiverMoible = AgencyConstants.DEFAULT_STRING_VALUE;
	private String receiverName = AgencyConstants.DEFAULT_STRING_VALUE;
	private String registrationNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	private String tndsSocho = AgencyConstants.DEFAULT_STRING_VALUE;
	private Boolean tndsbbCheck = AgencyConstants.DEFAULT_BOOLEAN_VALUE;
	private Boolean tndstnCheck = AgencyConstants.DEFAULT_BOOLEAN_VALUE;
	private Double tndstnPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double tndstnSotien = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double totalPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private Double thirdPartyPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	private String thoihanden = AgencyConstants.DEFAULT_STRING_VALUE;
	private String thoihantu = AgencyConstants.DEFAULT_STRING_VALUE;
	private Boolean vcxCheck = AgencyConstants.DEFAULT_BOOLEAN_VALUE;
	private String yearOfMake = AgencyConstants.DEFAULT_STRING_VALUE;
	private String monthOfMake = AgencyConstants.DEFAULT_STRING_VALUE;
	private String categoryType = AgencyConstants.DEFAULT_STRING_VALUE;
	private String packageType;
	private String fileId1;
	private String fileId2;
	private String fileId3;
	private String fileId4;
	private String fileId5;
}