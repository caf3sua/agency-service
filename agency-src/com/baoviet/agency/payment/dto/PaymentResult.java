package com.baoviet.agency.payment.dto;

import com.baoviet.agency.payment.common.PaymentResponseType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class PaymentResult {
	PaymentResponseType responseType;
	
	String redirectUrl;
	
	String mciAddId;
	
	String policyNumber;
	
	String rspCode;
	
	String message;
}