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
 * The persistent class for the TVC_PLANE database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TvcPlaneDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tvcPlaneId;

	private Integer adults = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String agencyType = AgencyConstants.DEFAULT_STRING_VALUE;

	private String areaId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String areaName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Integer baby = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String baovietCompanyId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietCompanyName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietDepartmentId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String baovietDepartmentName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date bvSysdate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private Double changePremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Integer children = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String couponsCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double couponsValue = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String customerAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerCmt = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerPhone = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfPayment = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String invoiceAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private Integer invoiceCheck = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private String invoiceName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String invoiceTax = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double netPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String note = AgencyConstants.DEFAULT_STRING_VALUE;

	private String paymentMethod = AgencyConstants.DEFAULT_STRING_VALUE;

	private String paymentTransactionId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String placeFrom = AgencyConstants.DEFAULT_STRING_VALUE;

	private String placeTo = AgencyConstants.DEFAULT_STRING_VALUE;

	private String planId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String planName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String policyNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverPhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String sexId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String sexName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String soGycbh = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double totalPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String travelWithId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String travelWithName = AgencyConstants.DEFAULT_STRING_VALUE;
}