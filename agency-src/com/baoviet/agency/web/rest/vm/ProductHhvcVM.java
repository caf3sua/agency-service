package com.baoviet.agency.web.rest.vm;

import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ProductHhvcVM extends ProductBaseVM {

	private String agentId;
    private String agentName;
    private String bankId;
    private String bankName;
    private String baovietCompanyId;
    private String baovietCompanyName;
    private String baovietDepartmentId;
    private String baovietDepartmentName;
    private String baovietUserId;
    private String baovietUserName;
    @JsonSerialize(using = DoubleSerializer.class)
    private Double basicRate;
    private String beneficiaryAddr;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date beneficiaryDob;
    private String beneficiaryEmail;
    private String beneficiaryIdentity;
    private String beneficiaryMst;
    private String beneficiaryName;
    private String beneficiaryPhone;
    private String blAwbNo;
    private Integer conditionId;
    private String contactAddr;
    
    private String contactEmail;
    private String contactIdentity;
    private String contactMst;
    private String contactName;
    private String contactPhone;
    private String containerSerialNo;
    private String contractNo;
    private String convey;
    private String couponsCode;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double couponsValue;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double currencyRate;
    private String changePremiumContent;
    private String changePremiumId;
    private String changePremiumPaContent;
    private String changePremiumPaId;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double changePremiumPaPremium;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double changePremiumPaRate;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double changePremiumPremium;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double changePremiumRate;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfPayment;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfRequirement;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date departureDay;
    private String destination;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date expectedDate;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date expiredDate;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double feeReceive;
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
    private Integer goodsCategoryId;
    private String goodsCode;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double goodsCost;
    private String goodsCurrency;
    private String goodsDesc;
    private String goodsDvt;
    private Integer goodsId;
    private Integer goodsJourney;
    private Integer goodsMeanOfTransportation;
    private Integer goodsPackingMethod;
    private String goodsPaymentCurrency;
    private Integer goodsPaymentMethod;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double goodsPostage;
    private Integer goodsQuantities;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double goodsTransportationMethod;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double goodsWeight;
    private String gycbhEnVi;
    private String hanhTrinhVanChuyen;
    private String hhId;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date inceptionDate;
    private String insuredAddr;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date insuredDob;
    
    private String insuredEmail;
    private String insuredIdentity;
    private String insuredMst;
    private String insuredName;
    private String insuredPhone;
    
    private String invoiceNumber;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double isAdding10Percent;
    private String journeyNo;
    private String lcNo;
    private String loadingPort;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double loadingRateJourney;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double loadingRateTransportation;
    private String loanAccount;
    private String meanOfTransportation;
    private String mucChungTu;
    private String note;
    private String oldGycbhNumber;
    private String oldPolicyNumber;
    private String otherInsuranceAgreements;
    private String packagingMethod;
    private String placeOfPaymentIndemnity;
    private String policyNumber;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date policySendDate;
    private String policyStatus;
    private String policyStatusName;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumVat;
    private String productName;
    private String phuongTienVanChuyen;
    private String phuongThucDongGoi;
    private String phuongThucThanhToan;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double renewalsPremium;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double renewalsRate;
    private String renewalsReason;
    private String requirementAddr;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date requirementDob;
    private String requirementEmail;
    private String requirementIdentity;
    private String requirementMst;
    private String requirementName;
    private String requirementPhone;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date responseDate;
    
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date sendDate;
    
    private String soGycbh;
    private String startingGate;
    private String statusId;
    private String statusName;
    private String statusRenewalsId;
    private String statusRenewalsName;
    private String teamId;
    private String teamName;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double totalbasicPremium;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double totalNetPremium;
    
    @JsonSerialize(using = DoubleSerializer.class)
    private Double totalPremium;
    private String unloadingPort;
    private String paymentMenthod;
    
    private Boolean chkThemLaiUocTinh;
    
    private String nameNYCBH;
	private String addressNYCBH;
	private String mobileNYCBH;
	private String emailNguoiYCBH;
	private String passportNYCBH;
	private String maSoThueNYCBH;
	
	private String userAgent;
	
	@JsonSerialize(using = DoubleSerializer.class)
	private Double standardPremium;
	
	@JsonSerialize(using = DoubleSerializer.class)
	private Double netPremium;
	
	@JsonSerialize(using = DoubleSerializer.class)
	private Double totalVat;
	private String contactAddresstt;
}
