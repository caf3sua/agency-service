package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGREEMENT database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class AgreementDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String agencyId;

	private String agencyName;

	private String agencyP1Id;

	private String agencyP1Name;

	private String agencyP2Id;

	private String agencyP2Name;

	private String agencyP3Id;

	private String agencyP3Name;

	private String agencyP4Id;

	private String agencyP4Name;

	private String agencyP5Id;

	private String agencyP5Name;

	private String agencyP6Id;

	private String agencyP6Name;

	private String agentId;

	private String agentName;

	private String agreementId;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date agreementSysdate;

	private String bankId;

	private String bankName;

	private String baovietCompanyId;

	private String baovietCompanyName;

	private String baovietDepartmentId;

	private String baovietDepartmentName;

	private Double cancelPolicyCommision;

	private Double cancelPolicyCommision2;

	private Double cancelPolicyCommision3;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date cancelPolicyDate;

	private Double cancelPolicyPremium;

	private Double cancelPolicyPremium2;

	private Double cancelPolicyPremium3;

	private Double cancelPolicySupport;

	private Double cancelPolicySupport2;

	private Double cancelPolicySupport3;

	private Double changePremium;

	private Double claimRate;

	private Double claimRate1;

	private Double claimRate2;

	private Double commision;

	private Double commisionSupport;

	private String contactAddress;

	private String contactAddresstt;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date contactDob;

	private String contactId;

	private String contactName;

	private String contactPhone;

	private String contactUsername;

	private String couponsCode;

	private Double couponsValue;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfPayment;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfRequirement;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate;

	private Double feeReceive;

	private String gycbhId;

	private String gycbhNumber;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate;

	private String invoiceAccountNo;

	private String invoiceAddress;

	private String invoiceBuyer;

	private String invoiceCompany;

	private String invoiceTaxNo;

	private Integer isPolicy;

	private String lineId;

	private String lineName;

	private String mciAddId;

	private Double netPremium;

	private String oldGycbhId;

	private String oldGycbhNumber;

	private String oldPolicyNumber;

	private String oldPolicyStatusId;

	private String oldPolicyStatusName;

	private Double payDiscount;

	private String paymentGateway;

	private String paymentMethod;

	private String paymentTransactionId;

	private String policyNumber;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policySendDate;

	private String reasonCancel;

	private String receiveMethod;

	private String receiverAddress;

	private String receiverEmail;

	private String receiverMoible;

	private String receiverName;

	private String renewalsChoice;

	private String renewalsDes1;

	private String renewalsDes2;

	private Double renewalsPremium;

	private Double renewalsPremium1;

	private Double renewalsPremium2;

	private Double renewalsRate;

	private Double renewalsRate1;

	private Double renewalsRate2;

	private String renewalsReason;

	private Double renewalsSi;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date responseDate;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date sendDate;

	private Double standardPremium;

	private String statusGycbhId;

	private String statusGycbhName;

	private String statusPolicyId;

	private String statusPolicyName;

	private String statusRenewalsId;

	private String statusRenewalsId1;

	private String statusRenewalsId2;

	private String statusRenewalsName;

	private String statusRenewalsName1;

	private String statusRenewalsName2;

	private String subject;

	private String taxIdNumber;

	private String teamId;

	private String teamName;

	private Double totalPremium;

	private Double totalVat;

	private String typeOfPrint;

	private String userAgent;

	private String userId;

	private String userName;

	private String otp;
	
	private String otpStatus;
	
	private String otpStartTime;
	
	private String discount;
	
	private Integer createType;
	
	// More attribute private
	private Boolean canTaituc;
	
	private Boolean checkTVC;
	
	private Boolean checkPaymentLater;
	
	private String urnContact;
	
	private String urnDaily;
	
	private String urnDepartmentId;
	
	private Integer sendSms;
	
	private Integer sendEmail;
	
	private Integer sendWorkplace;
	
	private Integer sendEmailOtp;
	
	@Override
	public String toString() {
		return "AgreementDTO [agencyId=" + agencyId + ", agentId=" + agentId + ", agentName=" + agentName
				+ ", agreementId=" + agreementId + ", gycbhId=" + gycbhId + ", gycbhNumber=" + gycbhNumber
				+ ", invoiceTaxNo=" + invoiceTaxNo + ", receiverAddress=" + receiverAddress + ", totalPremium="
				+ totalPremium + ", totalVat=" + totalVat + ", typeOfPrint=" + typeOfPrint + ", userAgent=" + userAgent
				+ "]";
	}

}