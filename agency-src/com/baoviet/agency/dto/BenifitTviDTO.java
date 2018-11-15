package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BENIFIT_TVI database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BenifitTviDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String benifitTviId;

	private String areaId;

	private String areaName;

	private Integer fromDate;

	private Double premium;

	private Integer toDate;

	private String typeOfContactId;

	private String typeOfContactName;

	private String typeOfPlanId;

	private String typeOfPlanName;
}