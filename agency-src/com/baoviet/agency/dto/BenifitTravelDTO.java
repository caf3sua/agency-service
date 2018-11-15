package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the BenifitTravelDTO
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BenifitTravelDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String benifitTvcId;

	private String areaId;

	private String areaName;

	private Integer fromDate;

	private Integer toDate;

	private String typeOfContactId;

	private String typeOfContactName;

	private Double premium;

	private String typeOfPlanId;

	private String typeOfPlanName;
}