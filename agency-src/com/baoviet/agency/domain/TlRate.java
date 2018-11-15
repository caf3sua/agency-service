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
 * The persistent class for the TL_RATE database table.
 * 
 */
@Entity
@Table(name="TL_RATE")
@Getter
@Setter
public class TlRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TL_RATE_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;

	@Column
	private Integer frommonth;
	
	@Column
	private Integer tomonth;
	
	@Column
	private Double rate;

}