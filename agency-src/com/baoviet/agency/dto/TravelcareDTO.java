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
 * The persistent class for the TRAVELCARE database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TravelcareDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String travelcareId;
	
	private String accountName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String accountNumber = AgencyConstants.DEFAULT_STRING_VALUE;

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

	private Double changePremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String couponsCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double couponsValue = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfPayment = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String destinationId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String destinationName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private Double feeReceive = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String invoiceAccountNo = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceBuyer = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceCompany = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceTaxNo = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double messageId = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double netPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String note = AgencyConstants.DEFAULT_STRING_VALUE;

	private String paymentMethod = AgencyConstants.DEFAULT_STRING_VALUE;

	private String planId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String planName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policyDeliver = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String policyNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double premium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String propserAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String propserCellphone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String propserCmt = AgencyConstants.DEFAULT_STRING_VALUE;

	private String propserEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String propserHomephone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String propserId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String propserName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date propserNgaysinh = AgencyConstants.DEFAULT_DOB_VALUE;

	private String propserProvince = AgencyConstants.DEFAULT_STRING_VALUE;

	private String propserTitle = AgencyConstants.DEFAULT_STRING_VALUE;

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

	private String teamId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String teamName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String travelWithId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String travelWithName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String userId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String userName = AgencyConstants.DEFAULT_STRING_VALUE;
}