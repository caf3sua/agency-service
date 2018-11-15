package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the PA_RATE database table.
 * 
 */
@Entity
@Table(name="SPP_PRICES")
@Getter
@Setter
public class SppPrices implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "CARS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="PRICE_ID", unique=true)	
	private String priceId;
	@Column
	private String car;
	@Column
	private String status;
	@Column
	private Integer priceYear;
	@Column
	private Double priceUsd;
	@Column
	private Double priceVnd;
	@Column
	private Double priceExchange;
	@Column
	private Date priceDateCreated;
	@Column
	private Date priceSysdate;
	@Column
	private String priceUser;
	@Column
	private String method;
}