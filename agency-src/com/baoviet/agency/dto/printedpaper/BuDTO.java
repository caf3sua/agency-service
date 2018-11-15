package com.baoviet.agency.dto.printedpaper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
public class BuDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("OUTLET_TYPE_CODE")
	private String outletTypeCode;
	
	@JsonProperty("OUTLET_AMS_ID")
	private String outletAmsId;
	
	@JsonProperty("OUTLET_NAME")
	private String outletName;
	
	@JsonProperty("PR_OUTLET_ID")
	private Double prOutletId;
	
	@JsonProperty("PR_OUTLET_TYPE_CODE")
	private String prOutletTypeCode;
	
	@JsonProperty("PR_OUTLET_AMS_ID")
	private String prOutletAmsId;
	
	@JsonProperty("PR_OUTLET_NAME")
	private String prOutletName;
	
	@JsonProperty("URN")
	private String urn;
}