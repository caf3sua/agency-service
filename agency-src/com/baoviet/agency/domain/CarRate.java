package com.baoviet.agency.domain;

import java.io.Serializable;

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
@Table(name="CAR_RATE")
@Getter
@Setter
public class CarRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "CAR_RATE_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="CAR_RATE_ID", unique=true)
	private String carRateId;
	
	@Column
	private String purposeOfUsageId;
	
	@Column
	private Integer seatNumberFrom;
	
	@Column
	private Integer seatNumberTo;
	
	@Column
	private Double netPremium;
	
	@Column
	private Double grossPremium;
	
	@Column
	private String type;
	
	@Column
	private String agencyId;


}