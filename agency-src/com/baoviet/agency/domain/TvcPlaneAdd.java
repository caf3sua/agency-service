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
 * The persistent class for the TVC_PLANE_ADD database table.
 * 
 */
@Entity
@Table(name="TVC_PLANE_ADD")
@Getter
@Setter
public class TvcPlaneAdd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TVC_PLANE_ADD_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TVC_PLANE_ADD_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String tvcPlaneAddId;

	@Column
	private String address;

	@Column
	private Integer age;

	@Temporal(TemporalType.DATE)
	@Column(name="BV_SYSDATE")
	private Date bvSysdate;

	@Column(name="CELL_PHONE")
	private String cellPhone;

	@Column(name="CHANGE_PREMIUM")
	private Double changePremium;

	@Column
	private String city;

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

	@Column(name="NET_PREMIUM")
	private Double netPremium;

	@Column(name="RELATIONSHIP_ID")
	private String relationshipId;

	@Column(name="RELATIONSHIP_NAME")
	private String relationshipName;

	@Column(name="SEX_ID")
	private String sexId;

	@Column(name="SEX_NAME")
	private String sexName;

	@Column
	private String title;

	@Column(name="TOTAL_PREMIUM")
	private Double totalPremium;

	@Column(name="TVC_PLANE_ID")
	private String tvcPlaneId;
}