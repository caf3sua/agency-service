package com.baoviet.agency.payment.dto;

import java.io.Serializable;

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
public class ViettelResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("secure_hash")
	private String secureHash;
	
	private String status;
	
	@JsonProperty("order_id")
	private String orderId;
	
	@JsonProperty("merchant_code")
	private String merchantCode;
	
	private String version;
	
	@JsonProperty("vt_transaction_id")
	private String vtTransactionId;
}