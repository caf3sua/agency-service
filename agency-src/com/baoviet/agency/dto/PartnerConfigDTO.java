package com.baoviet.agency.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the PARTNER_CONFIG database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class PartnerConfigDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private BigDecimal commision;

	private BigDecimal discount;

	private String f1;

	private String f2;

	private String f3;

	private String f4;

	private String f5;

	private String idPartner;

	private String productCode;

	private String productName;

	private BigDecimal support;

}