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
 * The persistent class for the BENIFIT_TVI database table.
 * 
 */
@Entity
@Table(name="BENIFIT_TVI")
@Getter
@Setter
public class BenifitTvi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "BENIFIT_TVI_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="BENIFIT_TVI_ID")
	private String benifitTviId;

	@Column(name="AREA_ID")
	private String areaId;

	@Column(name="AREA_NAME")
	private String areaName;

	@Column(name="FROM_DATE")
	private Integer fromDate;

	@Column
	private Double premium;

	@Column(name="TO_DATE")
	private Integer toDate;

	@Column(name="TYPE_OF_CONTACT_ID")
	private String typeOfContactId;

	@Column(name="TYPE_OF_CONTACT_NAME")
	private String typeOfContactName;

	@Column(name="TYPE_OF_PLAN_ID")
	private String typeOfPlanId;

	@Column(name="TYPE_OF_PLAN_NAME")
	private String typeOfPlanName;
}