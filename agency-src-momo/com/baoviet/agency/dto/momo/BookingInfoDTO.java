package com.baoviet.agency.dto.momo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BookingInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("POLICY_NUMBER")
	private String policyNumber;
	
	@JsonProperty("TOTAL_PREMIUM")
	private String totalPremium;
	
	@JsonProperty("STANDARD_PREMIUM")
	private String standardPremium;
}
