package com.baoviet.agency.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BENIFIT_TRAVEL_CARE database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BenifitTravelCareDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal a;

	private BigDecimal a1;

	private BigDecimal a2;

	private BigDecimal b;

	private BigDecimal c;

}