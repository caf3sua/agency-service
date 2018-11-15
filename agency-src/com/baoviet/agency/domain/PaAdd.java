package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PA_ADD database table.
 * 
 */
@Entity
@Table(name="PA_ADD")
@Getter
@Setter
public class PaAdd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PA_ADD_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "PA_ADD_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String paAddId;

	@Column
	private String address;

	@Column(name="CELL_PHONE")
	private String cellPhone;

	@Column
	private String city;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name="EMAIL_ADRESS")
	private String emailAdress;

	@Column(name="HOME_PHONE")
	private String homePhone;

	@Column(name="ID_PASSWPORT")
	private String idPasswport;

	@Column(name="INSURED_NAME")
	private String insuredName;

	@Column(name="PA_ID")
	private String paId;

	@Column
	private BigDecimal premium;

	@Column
	private String relationship;

	@Column
	private BigDecimal si;

	@Column
	private String title;
}