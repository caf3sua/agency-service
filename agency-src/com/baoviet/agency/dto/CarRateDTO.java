package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the PA_RATE database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class CarRateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String carRateId;
	
	private String purposeOfUsageId;
	
	private Integer seatNumberFrom;
	
	private Integer seatNumberTo;
	
	private Double netPremium;
	
	private Double grossPremium;
	
	private String type;
	
	private String agencyId;

}