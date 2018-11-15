package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TL_ADD database table.
 * 
 */
@Entity
@Table(name="TL_ADD")
@Getter
@Setter
public class TlAdd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TL_ADD_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TL_ADD_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String tlAddId;
	
	@Column
	private String address;

	@Column(name="CELL_PHONE")
	private String cellPhone;

	@Column
	private String city;

	@Column
	private String diagnose;

	@Temporal(TemporalType.DATE)
	@Column
	private Date dob;

	@Column(name="EMAIL_ADRESS")
	private String emailAdress;

	@Column(name="HOME_PHONE")
	private String homePhone;

	@Column(name="ID_PASSWPORT")
	private String idPasswport;

	@Column(name="INSURED_NAME")
	private String insuredName;

	@Column
	private Double premium;

	@Column
	private String relationship;

	@Column
	private Double si;

	@Column
	private String title;

	@Column(name="TL_ID")
	private String tlId;
}