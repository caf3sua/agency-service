package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * The persistent class for the BENIFIT_TRAVEL_CARE database table.
 * 
 */
@Entity
@Table(name="BENIFIT_TRAVEL_CARE")
@Getter
@Setter
public class BenifitTravelCare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column
	private BigDecimal a;

	@Column
	private BigDecimal a1;

	@Column
	private BigDecimal a2;

	@Column
	private BigDecimal b;

	@Column
	private BigDecimal c;
	
	@Column(name="DAY_START")
	private long dayStart;

	@Column(name="DAY_END")
	private long dayEnd;

	@Column
	private String type;

}