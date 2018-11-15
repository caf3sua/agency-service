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
@Table(name="PURPOSE_OF_USAGE")
@Getter
@Setter
public class PurposeOfUsage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PURPOSE_OF_USAGE_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "PURPOSE_OF_USAGE_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String purposeOfUsageId;
	
	@Column
	private String purposeOfUsageName;
	
	@Column
	private String categoryId;
	
	@Column
	private Integer seatNumber;
	
	@Column
	private Integer seatNumberTo;


}