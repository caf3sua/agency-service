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
 * The persistent class for the MENU database table.
 * 
 */
@Entity
@Table(name="GIFTCODE")
@Getter
@Setter
public class GiftCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "GIFTCODE_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ID", unique=true)
	private String id;
	
	@Column(name="GIFTCODE")
	private String giftCode;
		
	@Column(name="FROM_DATE")
	private Date fromDate;
	
	@Column(name="TO_DATE")
	private Date toDate;
	
	@Column
	private String active;
	
	@Column(name="PRO_TYPE")
	private String proType;
	
	@Column
	private Integer discount;
	
	@Column
	private String email;
	
	@Column
	private String description;
	
	@Column(name="LINE_ID")
	private String lineId;
	
	@Column(name="ID_NUM")
	private String idNum;
	
	@Column
	private Integer name;
	
	@Column
	private Date mobile;
	
	@Column
	private Date stt;
}