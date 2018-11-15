package com.baoviet.agency.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the PREMIUM_TVC_GOTADI database table.
 * 
 */
@Getter
@Setter
public class PremiumTvcGotadiDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String premiumId;
	
	private String areaId;
	
	private String areaName;
	
	private String planId;
	
	private String planName;
	
	private Integer fromDate;
	
	private Integer toDate;
	
	private Double premium;
	
	private String typeOfAgency;
	
}