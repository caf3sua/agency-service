package com.baoviet.agency.payment.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViettelUpdateOrderStatusResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("response_code")
	private String responseCode;
	
	@JsonProperty("order_id")
	private String orderId;
	
	@JsonProperty("trans_amount")
	private String transAmount;
}