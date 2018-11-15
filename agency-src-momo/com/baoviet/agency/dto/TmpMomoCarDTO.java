package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TmpMomoCarDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String Id;

	private String requestId;

	private Date createdDate;

	// car base VM

	public Long actualValue;

	public Double changePremium;

	public String chassisNumber;

	public String engineNumber;

	public Integer garageCheck;

	public String insuredAddress;

	public String insuredName;

	private String invoiceCheck; // get; set; }

	private String invoiceName; // get; set; }

	private String invoiceCompany; // get; set; }

	private String invoiceTaxNo; // get; set; }

	private String invoiceAddress; // get; set; }

	private String invoiceAccountNo; // get; set; }

	public Integer khauHaoCheck;

	public Integer khauTruCheck;

	public String makeId;

	public String makeName;

	public Integer matCapCheck;

	public String modelId;

	public String modelName;

	public Integer nntxCheck;

	public Integer ngapNuocCheck;

	public Double passengersAccidentNumber;

	public Double passengersAccidentPremium;

	public Double passengersAccidentSi;

	public String policyNumber;

	public Double premium;

	public String purposeOfUsageId;

	public Long physicalDamagePremium;

	public Long physicalDamageSi;

	private String receiverName; // get; set; }

	private String receiverAddress; // get; set; }

	private String receiverEmail; // get; set; }

	private String receiverMobile; // get; set; }

	private String receiverAddressDistrict; // get; set; }

	public String registrationNumber;

	public String tndsSocho;

	public Integer tndsbbCheck;

	public Integer tndstnCheck;

	public Double tndstnPhi;

	public Double tndstnSotien;

	public Double totalPremium;

	public Double thirdPartyPremium;

	public String thoiHanDen;

	public String thoiHanTu;

	public Integer vcxCheck;

	public String yearOfMake;

	public String userAgent;

	// base

	private String contactCode;

	private String receiveMethod;

	private String gycbhNumber;

	private String oldGycbhNumber;

	private String contactName;

	private String contactEmail;

	private String contactAddress;

	private String contactPhonenumber;
	
	private String status;
	
	private Double tndsbbPhi;
	
	private Double vcxPhi;
	
	private String checkTaituc;
}
