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
 * The persistent class for the KCARE database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class KcareDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String kId;

	private String agentId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String agentName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String bankConfirm = AgencyConstants.DEFAULT_STRING_VALUE;

	private String bankId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String bankName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String bankNote = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietConfirm = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietDepartmentId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietDepartmentName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietNote = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryAddressD = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date beneficiaryDob = AgencyConstants.DEFAULT_DOB_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date beneficiaryDobD = AgencyConstants.DEFAULT_DOB_VALUE;

	private String beneficiaryEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryEmailD = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryIdNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryIdNumberD = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryNameD = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryPhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryPhoneD = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryRelationship = AgencyConstants.DEFAULT_STRING_VALUE;

	private String beneficiaryRelationshipD = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double changePremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String contactCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactGoitinhId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactGoitinhName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactMobilePhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactPhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String couponsCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double couponsValue = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfBirth = AgencyConstants.DEFAULT_DOB_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfPayment = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private Double feeReceive = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String insuredAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String insuredDob = AgencyConstants.DEFAULT_STRING_VALUE;

	private String insuredIdNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String insuredName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String insuredOccupation = AgencyConstants.DEFAULT_STRING_VALUE;

	private String insuredSex = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceAccountNo = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceBuyer = AgencyConstants.DEFAULT_STRING_VALUE;

	private Integer invoiceCheck = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String invoiceCompany = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceTaxNo = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double messageId = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double netPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String note = AgencyConstants.DEFAULT_STRING_VALUE;

	private String paymentMethod = AgencyConstants.DEFAULT_STRING_VALUE;

	private String permanentAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String planId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String planName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policyDeliver = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String policyNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String postalAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date propserNgaysinh = AgencyConstants.DEFAULT_DOB_VALUE;

	private String propserTitle = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date qDayTreatment = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String qDiagnose = AgencyConstants.DEFAULT_STRING_VALUE;

	private String qHopital = AgencyConstants.DEFAULT_STRING_VALUE;

	private String qPatient = AgencyConstants.DEFAULT_STRING_VALUE;

	private String qRelationship = AgencyConstants.DEFAULT_STRING_VALUE;

	private String qResultCan = AgencyConstants.DEFAULT_STRING_VALUE;

	private String qResultTre = AgencyConstants.DEFAULT_STRING_VALUE;

	private String qTreatment = AgencyConstants.DEFAULT_STRING_VALUE;

	private String qTypeCancer = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q1 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q10 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q2 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q3 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q4 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q5 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q6 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q7 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q8 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String q9 = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverMoible = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date reqDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

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

	private String statusPolicyId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusPolicyName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String taxIdNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String teamId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String teamName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double totalPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double totalVat = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String travelWithId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String travelWithName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String userId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String userName = AgencyConstants.DEFAULT_STRING_VALUE;
}