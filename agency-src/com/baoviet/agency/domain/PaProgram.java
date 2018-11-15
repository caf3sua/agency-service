package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * The persistent class for the PA_PROGRAM database table.
 * 
 */
@Entity
@Table(name="PA_PROGRAM")
@Getter
@Setter
public class PaProgram implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PA_PROGRAM_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "PA_PROGRAM_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String paProgramId;

	@Column(name="PA_PROGRAM_NAME")
	private String paProgramName;

	@Column(name="PA_PROGRAM_NAME_CODE")
	private String paProgramNameCode;

	@Column(name="PA_PROGRAM_PREMIUM")
	private BigDecimal paProgramPremium;

}