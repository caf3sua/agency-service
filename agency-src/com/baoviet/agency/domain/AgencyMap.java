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
 * The persistent class for the ADMIN_USER database table.
 * 
 */
@Entity
@Table(name="AGENCY_MAP")
@Getter
@Setter
public class AgencyMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "AGENCY_MAP_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ID", unique=true)
	private String id;
	
	@Column(name="AGENCY_P1_ID")
	private String agencyP1Id;
	
	@Column(name="AGENCY_P1_NAME")
	private String agencyP1Name;
	
	@Column(name="AGENCY_P2_ID")
	private String agencyP2Id;
	
	@Column(name="AGENCY_P2_NAME")
	private String agencyP2Name;
	
	@Column(name="AGENCY_P3_ID")
	private String agencyP3Id;
	
	@Column(name="AGENCY_P3_NAME")
	private String agencyP3Name;
	
	@Column(name="AGENCY_P4_ID")
	private String agencyP4Id;
	
	@Column(name="AGENCY_P4_NAME")
	private String agencyP4Name;
	
	@Column(name="AGENCY_P5_ID")
	private String agencyP5Id;
	
	@Column(name="AGENCY_P5_NAME")
	private String agencyP5Name;
	
	@Column(name="AGENCY_P6_ID")
	private String agencyP6Id;
	
	@Column(name="AGENCY_P6_NAME")
	private String agencyP6Name;
	
	@Column(name="AGENCY_ID")
	private String agencyId;
	
	@Column(name="AGENCY_NAME")
	private String agencyName;
	
	@Column(name="LINE_ID")
	private String lineId;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="BV_TEXTFIELD1")
	private String bvTextfield1;
	
	@Column(name="BV_TEXTFIELD2")
	private String bvTextfield2;
	
	@Column(name="BV_TEXTFIELD3")
	private String bvTextfield3;
	
	@Column(name="BV_TEXTFIELD4")
	private String bvTextfield4;
	
	@Column(name="BV_TEXTFIELD5")
	private String bvTextfield5;
	
	@Column(name="BV_TEXTFIELD6")
	private String bvTextfield6;
	
	@Column(name="BV_TEXTFIELD7")
	private String bvTextfield7;
	
	@Column(name="BV_TEXTFIELD8")
	private String bvTextfield8;
	
	@Column(name="BV_TEXTFIELD9")
	private String bvTextfield9;
	
	@Column(name="BV_TEXTFIELD10")
	private String bvTextfield10;
	
	@Column(name="BV_TEXTFIELD11")
	private String bvTextfield11;
	
	@Column(name="BV_TEXTFIELD12")
	private String bvTextfield12;
	
	@Column(name="BV_TEXTFIELD13")
	private String bvTextfield13;
	
	@Column(name="BV_TEXTFIELD14")
	private String bvTextfield14;
	
	@Column(name="BV_SYSDATE")
	private Date bvSysdate;
	
	@Column(name="BV_ID1")
	private String bvId1;
	
	@Column(name="BV_NAME1")
	private String bvName1;
	
	@Column(name="BV_ID2")
	private String bvId2;
	
	@Column(name="BV_NAME2")
	private String bvName2;
	
	@Column(name="BV_ID3")
	private String bvId3;
	
	@Column(name="BV_NAME3")
	private String bvName3;
	
	@Column(name="BV_ID4")
	private String bvId4;
	
	@Column(name="BV_NAME4")
	private String bvName4;
	
	@Column(name="BV_ID5")
	private String bvId5;
	
	@Column(name="BV_NAME5")
	private String bvName5;
	
	@Column(name="BV_ID6")
	private String bvId6;
	
	@Column(name="BV_NAME6")
	private String bvName6;
}