package com.baoviet.agency.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BENIFIT_HOME database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BenifitHomeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String benifitHomeId;

	private String benifitHomeName;

	private BigDecimal homeYearOldFrom;

	private BigDecimal homeYearOldTo;

	private BigDecimal rate;

	private BigDecimal yearFrom;

	private BigDecimal yearTo;

}