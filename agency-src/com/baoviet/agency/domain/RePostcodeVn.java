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

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the RE_POSTCODE_VN database table.
 * 
 */
@Entity
@Table(name="RE_POSTCODE_VN")
@Getter
@Setter
public class RePostcodeVn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PK_POSTCODE", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String pkPostcode;
	
	@Column(name="PK_DISTRICT")
	private String pkDistrict;
	
	@Column(name="PK_PROVINCE")
	private String pkProvince;

	@Temporal(TemporalType.DATE)
	@Column
	private Date pkStartDate;
	
	@Column(name="IS_OBJECT_LOCKED")
	private Integer isObjectLocked;
	
	@Column(name="LOCKED_BY")
	private String lockedBy;
	
	@Column(name="EXT_CODE_01")
	private String extCode01;
	
	@Column(name="EXT_CODE_02")
	private String extCode02;
	
	@Column(name="EXT_CODE_03")
	private String extCode03;
	
	@Column(name="EXT_CODE_04")
	private String extCode04;
	
	@Column(name="EXT_CODE_05")
	private String extCode05;
	
	@Column(name="EXT_CODE_06")
	private String extCode06;
	
	@Column(name="EXT_CODE_07")
	private String extCode07;
	
	@Column(name="EXT_CODE_08")
	private String extCode08;
	
	@Column(name="EXT_CODE_09")
	private String extCode09;
	
	@Column(name="EXT_CODE_10")
	private String extCode10;
	
	@Column(name="EXT_CODE_11")
	private String extCode11;
	
	@Column(name="EXT_CODE_12")
	private String extCode12;
	
	@Column(name="EXT_CODE_13")
	private String extCode13;
	
	@Column(name="EXT_CODE_14")
	private String extCode14;
	
	@Column(name="EXT_CODE_15")
	private String extCode15;
	
	@Column(name="EXT_CODE_16")
	private String extCode16;
	
	@Column(name="EXT_CODE_17")
	private String extCode17;
	
	@Column(name="EXT_CODE_18")
	private String extCode18;
	
	@Column(name="EXT_CODE_19")
	private String extCode19;
	
	@Column(name="EXT_CODE_20")
	private String extCode20;
	
	@Column(name="DISTRICT_INDEX")
	private String districtIndex;
	
	@Column(name="PROVINCE_INDEX")
	private String provinceIndex;
	
	@Column(name="PK_PROVINCE_EN")
	private String pkProvinceEn;
	
	@Column(name="PK_DISTRICT_EN")
	private String pkDistrictEn;
}