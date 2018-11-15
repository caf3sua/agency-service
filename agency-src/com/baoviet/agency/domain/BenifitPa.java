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
 * The persistent class for the BENIFIT_PA database table.
 * 
 */
@Entity
@Table(name="BENIFIT_PA")
@Getter
@Setter
public class BenifitPa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "BENIFIT_PA_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="BENIFIT_PA_ID")
	private String benifitPaId;

	@Column(name="BENIFIT_NAME")
	private String benifitName;

	@Column
	private BigDecimal limit;

	@Column
	private String plan;
}