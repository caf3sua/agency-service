package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the TRAVEL_CARE_ADD_HIS database table.
 * 
 */
@Entity
@Table(name="TRAVEL_CARE_ADD_HIS")
@Getter
@Setter
public class TravelCareAddHi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TVC_ADD_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TRAVEL_CARE_ADD_HIS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String tvcAddId;

	@Column
	private String address;

	@Column(name="CELL_PHONE")
	private String cellPhone;

	@Column
	private String city;

	@Temporal(TemporalType.DATE)
	@Column(name="DAY_TREATMENT")
	private Date dayTreatment;

	@Column(name="DETAIL_TREATMENT")
	private String detailTreatment;

	@Column
	private String diagnose;

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

	@Column(name="NAME_DOCTOR")
	private String nameDoctor;

	@Column(name="PARENT_ID")
	private String parentId;

	@Column
	private String relationship;

	@Column(name="RESULT_TREATMENT")
	private String resultTreatment;

	@Column
	private String title;

	@Column(name="TRAVAELCARE_ID")
	private String travaelcareId;
}