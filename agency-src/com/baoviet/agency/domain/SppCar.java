package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the PA_RATE database table.
 * 
 */
@Entity
@Table(name="SPP_CARS")
@Getter
@Setter
public class SppCar implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
//	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "CARS_SEQ") })
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="CAR_ID", unique=true)	
	private String carId ;
	@Column(name="CAR_NAME")
    private String carName ;
	@Column(name="CAR_MANUFACTURER")
    private String carManufacturer ;
	@Column(name="CAR_TYPE")
    private String carType ;
	@Column(name="CAR_SYSDATE")
    private Date carSysdate ;
	@Column(name="CAPACITY")
    private String capacity ;
	@Column(name="MODEL")
    private String model ;
	@Column(name="MADE")
    private String made ;
	@Column(name="DESCRIPTION")
    private String description ;
	@Column(name="CAR_ID_BK")
	private String carIdBk;
	@Column(name="CAR_ID_OLD")
	private String carIdOld;
}