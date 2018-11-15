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
 * The persistent class for the BENIFIT_TRAVEL_CARE database table.
 * 
 */
@Entity
@Table(name="BENIFIT_TRAVEL")
@Getter
@Setter
public class BenifitTravel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "BENIFIT_TRAVEL_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column
	private String benifitTvcId;

	private String areaId;
	
	@Column
	private String areaName;
	
	@Column
	private Integer fromDate;
	
	@Column
	private Integer toDate;
	
	@Column
	private String typeOfContactId;
	
	@Column
	private String typeOfContactName;
	
	@Column
	private Double premium;
	
	@Column
	private String typeOfPlanId;
	
	@Column
	private String typeOfPlanName;
}