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
public class VnPayOrderStatusResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("RspCode")
	private String rspCode;
	
	@JsonProperty("Message")
	private String message;
}