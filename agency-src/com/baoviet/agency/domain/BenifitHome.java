package com.baoviet.agency.domain;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * The persistent class for the BENIFIT_HOME database table.
 * 
 */
@Entity
@Table(name="BENIFIT_HOME")
@Getter
@Setter
public class BenifitHome implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "BENIFIT_HOME_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="BENIFIT_HOME_ID")
	private String benifitHomeId;

	@Column(name="BENIFIT_HOME_NAME")
	private String benifitHomeName;

	@Column(name="HOME_YEAR_OLD_FROM")
	private BigDecimal homeYearOldFrom;

	@Column(name="HOME_YEAR_OLD_TO")
	private BigDecimal homeYearOldTo;

	@Column
	private BigDecimal rate;

	@Column(name="YEAR_FROM")
	private BigDecimal yearFrom;

	@Column(name="YEAR_TO")
	private BigDecimal yearTo;

}