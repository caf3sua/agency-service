package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the RE_POSTCODE_VN database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class RePostcodeVnDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pkPostcode;
	
	private String pkDistrict;
	
	private String pkProvince;
}