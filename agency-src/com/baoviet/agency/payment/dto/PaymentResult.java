package com.baoviet.agency.payment.dto;

import com.baoviet.agency.payment.common.PaymentResponseType;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
public class PaymentResult {
	PaymentResponseType responseType;
	
	String redirectUrl;
	
	String code;
	
	String mciAddId;
	
	String policyNumber;
}