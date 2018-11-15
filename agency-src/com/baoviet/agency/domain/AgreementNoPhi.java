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
 * The persistent class for the AGREEMENT database table.
 * 
 */
@Entity
@Table(name = "AGREEMENT_NO_PHI")
@Getter
@Setter
public class AgreementNoPhi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "AGREEMENT_NO_PHI_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ID", unique=true)
	private String id;
	
	@Column
	private String agreementId;
	
	@Column
	private String contactId;
	
	@Column
	private Date createDate;
	
	@Column
	private String note;
	
	@Column
	private double sotien;
}