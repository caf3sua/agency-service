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
 * The persistent class for the ATCS database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class AtcsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String atcsId;
	private String requesterName;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date requesterDob;
	private String requesterGender;
	private String requesterIdentity;
	private String requesterPassport;
	private String requesterAddress;
	private String loanAgreement;
	private String requesterReference;
	private String insuranceName;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date insuranceDob;
	private String insuranceGender;
	private String insuranceIdentity;
	private String insurancePassport;
	private String insuranceAddress;
	private String insuranceMobile;
	private String policyNumber;
	private String insuranceJob;
	private String beneficiaryTypeCode;
	private String beneficiaryType;
	private String beneficiaryName;
	private String beneficiaryReference;
	private String beneficiaryReferenceCode;
	private String beneficiaryIdentity;
	private String rankCode;
	private String rankName;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate;
	private String totalYear;
	private Double standardPremium;
	private Double netPremium;
	private Double changePremium;
	private Double totalPremium;
	private Double payDiscount;
	private String contactId;
	private String contactCode;
	private String contactName;
	private String contactType;
	private String mrtbankEmpCode;
	private String mrtbankEmpName;
	private String policyStatus;
	private String policyStatusName;
	private String createdBy;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createdDate;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date modifiedDate;
	private String requesterMobile;
	private String requesterReferenceCode;
	private String modifiedBy;
	private String soGycbh;
	private String insuranceGenderCode;
	private String requesterGenderCode;
	private String status;
	private String statusName;
	private String requesterEmail;
	private String insuranceEmail;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date beneficiaryDob;
	private String transactionOffice;
	private String transactionOfficeName;
	private String branch;
	private String branchName;
	private String employeeMobile;
}