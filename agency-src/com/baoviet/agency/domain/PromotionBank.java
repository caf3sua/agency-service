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
 * The persistent class for the TL_ADD database table.
 * 
 */
@Entity
@Table(name="PROMOTION_BANK")
@Getter
@Setter
public class PromotionBank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "PROMOTION_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;
	
	@Column
	private Date fromDate;
	
	@Column
	private Date toDate;
	
	@Column
	private Double discount;
	
	@Column
	private String active;
	
	@Column
	private String title;
	
	@Column(name="LINE_LISTID")
	private String lineListId;
	
	@Column
	private String idType;
}