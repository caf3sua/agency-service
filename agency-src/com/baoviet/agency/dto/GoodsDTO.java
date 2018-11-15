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
 * The persistent class for the MENU database table.
 * 
 */

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class GoodsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String hhId;
	
	private Integer goodsId = AgencyConstants.DEFAULT_INTEGER_VALUE;
		
	private String soGycbh = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String statusId = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String statusName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date DateOfRequirement = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date DateOfPayment = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	private String policyNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String policyStatus = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String policyStatusName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private Integer goodsCategoryId = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private Integer conditionId = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private Integer goodsPaymentMethod = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private Integer goodsPackingMethod = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private Integer goodsTransportationMethod = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private Integer goodsMeanOfTransportation = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private Double goodsCost = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	private Double goodsPostage = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	private String goodsCurrency = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private Integer isAdding10Percent = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private String goodsPaymentCurrency = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private Double loadingRateJourney = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	private Double loadingRateTransportation = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	private Double basicRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	private Double currencyRate = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	private String requirementName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date requirementDob = AgencyConstants.DEFAULT_DOB_VALUE;
	
	private String requirementAddr = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String requirementPhone = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String requirementEmail = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String requirementIdentity = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String insuredName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String insuredIdentity = AgencyConstants.DEFAULT_STRING_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date insuredDob = AgencyConstants.DEFAULT_DOB_VALUE;
	
	private String insuredAddr = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String insuredPhone = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String insuredEmail = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String beneficiaryName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String beneficiaryIdentity = AgencyConstants.DEFAULT_STRING_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date beneficiaryDob = AgencyConstants.DEFAULT_DOB_VALUE;
	
	private String beneficiaryAddr = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String beneficiaryPhone = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String beneficiaryEmail = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String contactName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String contactIdentity = AgencyConstants.DEFAULT_STRING_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date contactDob = AgencyConstants.DEFAULT_DOB_VALUE;
	
	private String contactAddr = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String contactPhone = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String contactEmail = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String goodsDesc = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String goodsCode = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String packagingMethod = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private Double goodsWeight = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	private Integer goodsQuantities = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private String containerSerialNo = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String meanOfTransportation = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String journeyNo = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String blAwbNo = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String startingGate = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String destination = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String contractNo = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String loadingPort = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String unloadingPort = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String lcNo = AgencyConstants.DEFAULT_STRING_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date departureDay = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	private String convey = AgencyConstants.DEFAULT_STRING_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expectedDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	private String otherInsuranceAgreements = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String placeOfPaymentIndemnity = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String gycbhEnVi = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private Double totalNetPremium = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	private String changePremiumId = AgencyConstants.DEFAULT_STRING_VALUE;
	
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
	
	private String invoiceCompany = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String invoiceAddress = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String invoiceNumber = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private Integer invoiceCheck = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private Double feeReceive = AgencyConstants.DEFAULT_DOUBLE_VALUE;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policySendDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;
	
	private String receiverName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String receiverAddress = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String receiverEmail = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String receiverMoible = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String couponsCode = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private Double couponsValue = AgencyConstants.DEFAULT_DOUBLE_VALUE;	
	
	private Integer goodsJourney = AgencyConstants.DEFAULT_INTEGER_VALUE;
	
	private String field1 = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String field2 = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String field3 = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String field4 = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String field5 = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String requirementMst = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String insuredMst = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String beneficiaryMst = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String contactMst = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String goodsDvt = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String productName = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String phuongThucThanhToan = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String phuongThucDongGoi = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String hanhTrinhVanChuyen = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String phuongTienVanChuyen = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String mucChungTu = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String goodsCurrencyExchange = AgencyConstants.DEFAULT_STRING_VALUE;
	
	private String goodsPaymentExchange = AgencyConstants.DEFAULT_STRING_VALUE;
}