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
 * The persistent class for the HOME database table.
 * 
 */
@Entity
@Table(name="HOME")
@Getter
@Setter
public class Home implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "HOME_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="HOME_ID", unique=true)
	private String homeId;
	
	@Column
	private String soGycbh;
	
	@Column
	private String statusId;
	
	@Column
	private String statusName;
	
	@Column
	private Date dateOfRequirement;
	
	@Column
	private Date inceptionDate;
	
	@Column
	private String policyNumber;
	
	@Column
	private String policyStatus;
	
	@Column
	private String policyStatusName;
	
	@Column
	private Integer entitlements;
	
	@Column
	private String insuredLocation;
	
	@Column
	private Double totalUsedArea;
	
	@Column
	private Integer yearOfMake;
	
	@Column
	private Integer windowLocks;
	
	@Column
	private Integer bars;
	
	@Column
	private Integer mesh;
	
	@Column
	private Integer unprotected;
	
	@Column
	private String byDay;
	
	@Column
	private String byNight;
	
	@Column
	private Double conSi;
	
	@Column
	private Integer conPeriodOfCoverage;
	
	@Column
	private Integer conYearOlds;

	@Column
	private Double conRate;

	@Column
	private Integer homePeriodOfCoverage;

	@Column
	private String plan;

	@Column
	private Double planRate;

	@Column
	private Double totalNetPremium;

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
	private String contactId;
	
	@Column
	private String contactCode;
	
	@Column
	private String taxIdNumber;
	
	@Column
	private String postalAddress;
	
	@Column
	private String permanentAddress;
	
	@Column
	private String contactName;
	
	@Column
	private String homePhone;
	
	@Column
	private String mobilePhone;
	
	@Column
	private String ownership;
	
	@Column
	private String loanAccount;

	@Column
	private Date smartappSysdate;
	
	@Column
	private String pathOwner;
	
	@Column
	private String pathCreated;
	
	@Column
	private Date expiredDate;

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
	private String oldPolicyNumber;
	
	@Column
	private String oldGycbhNumber;
	
	@Column
	private String note;
	
	@Column
	private String renewalsReason;

	@Column
	private Double conPremium;
	
	@Column
	private Double planPremium;

	@Column
	private String baovietUserId;
	
	@Column
	private String baovietUserName;

	@Column
	private String changePremium_type;

	@Column
	private Double llimitPerItem;
	
	@Column
	private Double liabilityPerClaim;
	
	@Column
	private Double deductible;

	@Column
	private Date payment_date;

	@Column
	private String invoiceCompany;
	
	@Column
	private String invoiceAddress;
	
	@Column
	private String invoceNumber;
	
	@Column
	private Integer voiceCheck;
	
	@Column
	private Double feeReceive;

	@Column
	private Date policy_send_date;

	@Column
	private String peoplePermanently;
	
	@Column
	private String houseMaid;

	@Column
	private Double liability;
	
	@Column
	private Integer liabilityPeriodOfCoverage;
	
	@Column
	private Double liabilityFee;
	
	@Column
	private String liabilityPlan;

	@Column
	private Integer dkbs1;
	
	@Column
	private Integer dkbs2;
	
	@Column
	private Integer dkbs3;
	
	@Column
	private Integer dkbs4;

	@Column
	private String provinceId;
	
	@Column
	private String provinceName;

	@Column
	private Integer yearFinish;
	
	@Column
	private Integer soTang;
	
	@Column
	private Integer chieuRongNgo;

	@Column
	private Integer loaiHinh;

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
	private double si;
	
	@Column
	private double siin;
	
	@Column
	private String yearBuildCode;
	
	@Column
	private double premiumHome;

	@Override
	public String toString() {
		return "Home [homeId=" + homeId + ", soGycbh=" + soGycbh + ", statusId=" + statusId + ", statusName="
				+ statusName + ", dateOfRequirement=" + dateOfRequirement + ", inceptionDate=" + inceptionDate
				+ ", policyNumber=" + policyNumber + ", policyStatus=" + policyStatus + ", policyStatusName="
				+ policyStatusName + ", entitlements=" + entitlements + ", insuredLocation=" + insuredLocation
				+ ", totalUsedArea=" + totalUsedArea + ", yearOfMake=" + yearOfMake + ", windowLocks=" + windowLocks
				+ ", bars=" + bars + ", mesh=" + mesh + ", unprotected=" + unprotected + ", byDay=" + byDay
				+ ", byNight=" + byNight + ", conSi=" + conSi + ", conPeriodOfCoverage=" + conPeriodOfCoverage
				+ ", conYearOlds=" + conYearOlds + ", conRate=" + conRate + ", homePeriodOfCoverage="
				+ homePeriodOfCoverage + ", plan=" + plan + ", planRate=" + planRate + ", totalNetPremium="
				+ totalNetPremium + ", changePremiumContent=" + changePremiumContent + ", changePremiumRate="
				+ changePremiumRate + ", changePremiumPremium=" + changePremiumPremium + ", totalBasicPremium="
				+ totalBasicPremium + ", premiumVat=" + premiumVat + ", totalPremium=" + totalPremium + ", bankId="
				+ bankId + ", bankName=" + bankName + ", teamId=" + teamId + ", teamName=" + teamName + ", agentId="
				+ agentId + ", agentName=" + agentName + ", contactId=" + contactId + ", contactCode=" + contactCode
				+ ", taxIdNumber=" + taxIdNumber + ", postalAddress=" + postalAddress + ", permanentAddress="
				+ permanentAddress + ", contactName=" + contactName + ", homePhone=" + homePhone + ", mobilePhone="
				+ mobilePhone + ", ownership=" + ownership + ", loanAccount=" + loanAccount + ", smartappSysdate="
				+ smartappSysdate + ", pathOwner=" + pathOwner + ", pathCreated=" + pathCreated + ", expiredDate="
				+ expiredDate + ", baovietCompanyId=" + baovietCompanyId + ", baovietCompanyName=" + baovietCompanyName
				+ ", baovietDepartmentId=" + baovietDepartmentId + ", baovietDepartmentName=" + baovietDepartmentName
				+ ", sendDate=" + sendDate + ", responseDate=" + responseDate + ", statusRenewalsId=" + statusRenewalsId
				+ ", statusRenewalsName=" + statusRenewalsName + ", oldPolicyNumber=" + oldPolicyNumber
				+ ", oldGycbhNumber=" + oldGycbhNumber + ", note=" + note + ", renewalsReason=" + renewalsReason
				+ ", conPremium=" + conPremium + ", planPremium=" + planPremium + ", baovietUserId=" + baovietUserId
				+ ", baovietUserName=" + baovietUserName + ", changePremium_type=" + changePremium_type
				+ ", llimitPerItem=" + llimitPerItem + ", liabilityPerClaim=" + liabilityPerClaim + ", deductible="
				+ deductible + ", payment_date=" + payment_date + ", invoiceCompany=" + invoiceCompany
				+ ", invoiceAddress=" + invoiceAddress + ", invoceNumber=" + invoceNumber + ", voiceCheck=" + voiceCheck
				+ ", feeReceive=" + feeReceive + ", policy_send_date=" + policy_send_date + ", peoplePermanently="
				+ peoplePermanently + ", houseMaid=" + houseMaid + ", liability=" + liability
				+ ", liabilityPeriodOfCoverage=" + liabilityPeriodOfCoverage + ", liabilityFee=" + liabilityFee
				+ ", liabilityPlan=" + liabilityPlan + ", dkbs1=" + dkbs1 + ", dkbs2=" + dkbs2 + ", dkbs3=" + dkbs3
				+ ", dkbs4=" + dkbs4 + ", provinceId=" + provinceId + ", provinceName=" + provinceName + ", yearFinish="
				+ yearFinish + ", soTang=" + soTang + ", chieuRongNgo=" + chieuRongNgo + ", loaiHinh=" + loaiHinh
				+ ", receiverName=" + receiverName + ", receiverAddress=" + receiverAddress + ", receiverEmail="
				+ receiverEmail + ", receiverMoible=" + receiverMoible + ", couponsCode=" + couponsCode
				+ ", couponsValue=" + couponsValue + "]";
	}

	

	
}