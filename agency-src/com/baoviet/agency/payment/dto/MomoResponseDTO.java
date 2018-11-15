package com.baoviet.agency.payment.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MomoResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("requestId")
	private String requestId;
	
	@JsonProperty("errorCode")
	private Integer errorCode;
	
	@JsonProperty("orderId")
	private String orderId;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("localMessage")
	private String localMessage;
	
	@JsonProperty("requestType")
	private String requestType;
	
	@JsonProperty("payUrl")
	private String payUrl;
	
	@JsonProperty("signature")
	private String signature;
	
	@JsonProperty("qrCodeUrl")
	private String qrCodeUrl;
	
	@JsonProperty("deeplink")
	private String deeplink;
	
	@JsonProperty("deeplinkWebInApp")
	private String deeplinkWebInApp;
}