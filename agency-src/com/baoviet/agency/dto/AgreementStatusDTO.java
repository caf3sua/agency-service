package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AgreementStatusDTO database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class AgreementStatusDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String agreementid;
	
	private String statusPolicyId;
	
	private String statusPolicyName;
	
	private String gycbhNumber;
	
	private String gycbhId;
	
	private String statusRenewalsId1;
	
	private String responseDate;

}