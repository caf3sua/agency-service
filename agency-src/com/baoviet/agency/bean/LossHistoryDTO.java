/* 
* Copyright 2011 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
*/
package com.baoviet.agency.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author: Nam, Nguyen Hoai
 */
@Getter
@Setter
public class LossHistoryDTO {

	private String id;
	private String claimId;
	private String policyNumber;
	private String policyVersion;
	private String claimFolderNumber;
	private String claimOfferNumber;
	private String requestDate;
	private String lossDate;
	private Integer paymentMethod;
	private Double claimAmount;
	private Double indemnifyAmount;
	private String currencyCode;
	private String note;
	private Integer status;
	private String systemDate;
	private String validFromDate;
	private String validToDate;
	private String riskCauseId;
	private String riskCauseName;
	private String hospitalId;
	private String hospitalName;
	private String addressOfLoss;
	private String benefitAcountName;
	private String benefitAcountNumber;
	private String benefitAcountBank;
	private String photoFolderName;
	private String identifiedNumber;
	private String claimPerson;
	private String claimPersonMobile;
	private String claimPersonRelationship;
	private String claimPersonAddress;
	private String claimPersonEmail;
	private String reasonLoss;
	private Integer isget;
	private String errorMsg;
}