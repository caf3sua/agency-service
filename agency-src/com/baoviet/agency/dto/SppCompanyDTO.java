package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the SppCompanyDTO database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class SppCompanyDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String companyId;
	
	private String companyName;
	
	private String address;
	
	private String phone;
	
	private String companyCode;
	
	private String tenTat;
	
	private String btcCodeTinhthanh;
	
	private String btcCodeChinhanh;
	
	private String maCongty;
	
	private String type;
}