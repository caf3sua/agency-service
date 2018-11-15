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
public class TlAnnualRateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String conditionId;

	private String conditionName;

	private Integer ageFrom;

	private Integer ageTo;

	private Double rate;

}