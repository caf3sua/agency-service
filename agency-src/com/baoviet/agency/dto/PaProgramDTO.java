package com.baoviet.agency.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the PA_PROGRAM database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class PaProgramDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String paProgramId;

	private String paProgramName;

	private String paProgramNameCode;

	private BigDecimal paProgramPremium;

}