package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the PARTNER database table.
 * 
 */
@Entity
@Getter
@Setter
public class Partner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "PARTNER_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;

	@Column
	private String address;

	@Column
	private String code;

	@Temporal(TemporalType.DATE)
	@Column
	private Date dor;

	@Temporal(TemporalType.DATE)
	@Column
	private Date dos;

	@Column
	private String f1;

	@Column
	private String f2;

	@Column
	private String f3;

	@Column
	private String f4;

	@Column
	private String f5;

	@Column
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="SYSTEM_DATE")
	private Date systemDate;

	@Column
	private String tax;

	@Column
	private String token;

	@Column
	private String type;

}