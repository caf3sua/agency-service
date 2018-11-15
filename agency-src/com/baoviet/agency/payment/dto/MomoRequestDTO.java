package com.baoviet.agency.payment.dto;

import java.io.Serializable;

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
public class MomoRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String partnerCode;

	private String accessKey;

	private String requestId;

	private String amount;

	private String orderId;

	private String orderInfo;

	private String returnUrl;

	private String notifyUrl;

	private String requestType;

	private String signature;
}