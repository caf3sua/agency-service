package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the ATCS database table.
 * 
 */
@Entity
@Table(name="ATCS")
@Getter
@Setter
public class Atcs implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ATCS_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "ATCS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String atcsId;
	
	@Column
	private String requesterName;

	@Temporal(TemporalType.DATE)
	@Column
	private Date requesterDob;
	
	@Column
	private String requesterGender;
	
	@Column
	private String requesterIdentity;
	
	@Column
	private String requesterPassport;
	
	@Column
	private String requesterAddress;
	
	@Column
	private String loanAgreement;
	
	@Column
	private String requesterReference;
	
	@Column
	private String insuranceName;

	@Temporal(TemporalType.DATE)
	@Column
	private Date insuranceDob;
	
	@Column
	private String insuranceGender;
	
	@Column
	private String insuranceIdentity;
	
	@Column
	private String insurancePassport;
	
	@Column
	private String insuranceAddress;
	
	@Column
	private String insuranceMobile;
	
	@Column
	private String policyNumber;
	
	@Column
	private String insuranceJob;
	
	@Column
	private String beneficiaryTypeCode;
	
	@Column
	private String beneficiaryType;
	
	@Column
	private String beneficiaryName;
	
	@Column
	private String beneficiaryReference;
	
	@Column
	private String beneficiaryReferenceCode;
	
	@Column
	private String beneficiaryIdentity;
	
	@Column
	private String rankCode;
	
	@Column
	private String rankName;

	@Temporal(TemporalType.DATE)
	@Column
	private Date inceptionDate;

	@Temporal(TemporalType.DATE)
	@Column
	private Date expiredDate;
	
	@Column
	private String totalYear;
	
	@Column
	private Double standardPremium;
	
	@Column
	private Double netPremium;
	
	@Column
	private Double changePremium;
	
	@Column
	private Double totalPremium;
	
	@Column
	private Double payDiscount;
	
	@Column
	private String contactId;
	
	@Column
	private String contactCode;
	
	@Column
	private String contactName;
	
	@Column
	private String contactType;
	
	@Column
	private String mrtbankEmpCode;
	
	@Column
	private String mrtbankEmpName;
	
	@Column
	private String policyStatus;
	
	@Column
	private String policyStatusName;
	
	@Column
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	@Column
	private Date modifiedDate;
	
	@Column
	private String requesterMobile;
	
	@Column
	private String requesterReferenceCode;
	
	@Column
	private String modifiedBy;
	
	@Column
	private String soGycbh;
	
	@Column
	private String insuranceGenderCode;
	
	@Column
	private String requesterGenderCode;
	
	@Column
	private String status;
	
	@Column
	private String statusName;
	
	@Column
	private String requesterEmail;
	
	@Column
	private String insuranceEmail;
	
	@Temporal(TemporalType.DATE)
	@Column
	private Date beneficiaryDob;
	
	@Column
	private String transactionOffice;
	
	@Column
	private String transactionOfficeName;
	
	@Column
	private String branch;
	
	@Column
	private String branchName;
	
	@Column
	private String employeeMobile;
}