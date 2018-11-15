package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the PA_RATE database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class SppPricesDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String priceId;
	private String car;
	private String status;
	private Integer priceYear;
	private Double priceUsd;
	private Double priceVnd;
	private Double priceExchange;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date priceDateCreated;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date priceSysdate;
	private String priceUser;
	private String method;
}