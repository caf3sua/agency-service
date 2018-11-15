package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the GoodsBasicRateDTO database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class GoodsBasicRateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String rateId;

	private Integer category;
	
	private String categoryName;
	
	private Integer packedType;
	
	private String packedTypeName;
	
	private Integer transport;
	
	private String transportName;
	
	private Integer over500km;
	
	private Double rate;

}