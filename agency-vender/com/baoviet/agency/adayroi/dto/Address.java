package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer ProvinceID;

	private String ProvinceName;

	private Integer DistrictID;

	private String DistrictName;

	private Integer WardID;
	
	private String WardName;
	
	private String AddressDetail;
}
