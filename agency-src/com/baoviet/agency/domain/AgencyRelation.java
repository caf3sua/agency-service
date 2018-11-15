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
 * The persistent class for the AGENCY_RELATION database table.
 * 
 */
@Entity
@Table(name="AGENCY_RELATION")
@Getter
@Setter
public class AgencyRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "AGENCY_RELATION_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;

	@Column
	private String name;
	
	@Column
	private String address;
	
	@Column
	private String phone;
	
	@Column
	private String fax;
	
	@Column
	private String email;
	
	@Column
	private String bankAccount;
	
	@Column
	private String bankName;
	
	@Column
	private String bankBranch;
	
	@Column
	private String taxNumber;
	
	@Column(name="FS1")
	private String fs1;
	
	@Column(name="FS2")
	private String fs2;
	
	@Column(name="FS3")
	private String fs3;
	
	@Column(name="FS4")
	private String fs4;
	
	@Column(name="FS5")
	private String fs5;
	
	@Column(name="FS6")
	private String fs6;
	
	@Column(name="FS7")
	private String fs7;
	
	@Column(name="FS8")
	private String fs8;
	
	@Column(name="FS9")
	private String fs9;
	
	@Column(name="FS10")
	private String fs10;
	
	@Column
	private String parrenetId;
	
	@Column
	private String type;
	
	@Column(name="FS11")
	private String fs11;
	
	@Column(name="FS12")
	private String fs12;
	
	@Column(name="FS13")
	private String fs13;
	
	@Column(name="FS14")
	private String fs14;
	
	@Column(name="FS15")
	private String fs15;
	
	@Column
	private String idAddress;
}