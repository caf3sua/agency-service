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

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MENU database table.
 * 
 */
@Entity
@Table(name="GOODS")
@Getter
@Setter
public class Goods implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "GOODS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="HH_ID", unique=true)
	private String hhId;
	
	@Column
	private Integer goodsId;
		
	@Column
	private String soGycbh;
	
	@Column
	private String statusId;
	
	@Column
	private String statusName;
	
	@Column
	private Date DateOfRequirement;
	
	@Column
	private Date inceptionDate;
	
	@Column
	private Date expiredDate;
	
	@Column
	private Date DateOfPayment;
	
	@Column
	private String policyNumber;
	
	@Column
	private String policyStatus;
	
	@Column
	private String policyStatusName;
	
	@Column
	private Integer goodsCategoryId;
	
	@Column
	private Integer conditionId;
	
	@Column
	private Integer goodsPaymentMethod;
	
	@Column
	private Integer goodsPackingMethod;
	
	@Column
	private Integer goodsTransportationMethod;
	
	@Column
	private Integer goodsMeanOfTransportation;
	
	@Column
	private Double goodsCost;
	
	@Column
	private Double goodsPostage;
	
	@Column
	private String goodsCurrency;
	
	@Column(name="IS_ADDING_10_PERCENT")
	private Integer isAdding10Percent;
	
	@Column
	private String goodsPaymentCurrency;
	
	@Column
	private Double loadingRateJourney;
	
	@Column
	private Double loadingRateTransportation;
	
	@Column
	private Double basicRate;
	
	@Column
	private Double currencyRate;
	
	@Column
	private String requirementName;
	
	@Column
	private Date requirementDob;
	
	@Column
	private String requirementAddr;
	
	@Column
	private String requirementPhone;
	
	@Column
	private String requirementEmail;
	
	@Column
	private String requirementIdentity;
	
	@Column
	private String insuredName;
	
	@Column
	private String insuredIdentity;
	
	@Column
	private Date insuredDob;
	
	@Column
	private String insuredAddr;
	
	@Column
	private String insuredPhone;
	
	@Column
	private String insuredEmail;
	
	@Column
	private String beneficiaryName;
	
	@Column
	private String beneficiaryIdentity;
	
	@Column
	private Date beneficiaryDob;
	
	@Column
	private String beneficiaryAddr;
	
	@Column
	private String beneficiaryPhone;
	
	@Column
	private String beneficiaryEmail;
	
	@Column
	private String contactName;
	
	@Column
	private String contactIdentity;
	
	@Column
	private Date contactDob;
	
	@Column
	private String contactAddr;
	
	@Column
	private String contactPhone;
	
	@Column
	private String contactEmail;
	
	@Column
	private String goodsDesc;
	
	@Column
	private String goodsCode;
	
	@Column
	private String packagingMethod;
	
	@Column
	private Double goodsWeight;
	
	@Column
	private Integer goodsQuantities;
	
	@Column
	private String containerSerialNo;
	
	@Column
	private String meanOfTransportation;
	
	@Column
	private String journeyNo;
	
	@Column
	private String blAwbNo;
	
	@Column
	private String startingGate;
	
	@Column
	private String destination;
	
	@Column
	private String contractNo;
	
	@Column
	private String loadingPort;
	
	@Column
	private String unloadingPort;
	
	@Column
	private String lcNo;
	
	@Column
	private Date departureDay;
	
	@Column
	private String convey;
	
	@Column
	private Date expectedDate;
	
	@Column
	private String otherInsuranceAgreements;
	
	@Column
	private String placeOfPaymentIndemnity;
	
	@Column
	private String gycbhEnVi;
	
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
	private Integer invoiceCheck;
	
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
	private Integer goodsJourney;
	
	@Column(name="FIELD_1")
	private String field1;
	
	@Column(name="FIELD_2")
	private String field2;
	
	@Column(name="FIELD_3")
	private String field3;
	
	@Column(name="FIELD_4")
	private String field4;
	
	@Column(name="FIELD_5")
	private String field5;
	
	@Column
	private String requirementMst;
	
	@Column
	private String insuredMst;
	
	@Column
	private String beneficiaryMst;
	
	@Column
	private String contactMst;
	
	@Column
	private String goodsDvt;
	
	@Column
	private String productName;
	
	@Column
	private String phuongThucThanhToan;
	
	@Column
	private String phuongThucDongGoi;
	
	@Column
	private String hanhTrinhVanChuyen;
	
	@Column
	private String phuongTienVanChuyen;
	
	@Column
	private String mucChungTu;
	
	@Column
	private String goodsCurrencyExchange;
	
	@Column
	private String goodsPaymentExchange;
}