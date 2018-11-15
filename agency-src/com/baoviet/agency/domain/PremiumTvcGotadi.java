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
 * The persistent class for the PREMIUM_TVC_GOTADI database table.
 * 
 */
@Entity
@Table(name="PREMIUM_TVC_GOTADI")
@Getter
@Setter
public class PremiumTvcGotadi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "PREMIUM_TVC_GOTADI_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="PREMIUM_ID", unique=true)
	private String premiumId;
	
	@Column
	private String areaId;
	
	@Column
	private String areaName;
	
	@Column
	private String planId;
	
	@Column
	private String planName;
	
	@Column
	private Integer fromDate;
	
	@Column
	private Integer toDate;
	
	@Column
	private Double premium;
	
	@Column
	private String typeOfAgency;
	
}