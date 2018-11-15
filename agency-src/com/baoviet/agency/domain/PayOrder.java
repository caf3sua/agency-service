package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TL_ADD database table.
 * 
 */
@Entity
@Table(name="PAYORDER")
@Getter
@Setter
public class PayOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@SequenceGenerator(name= "NAME_SEQUENCE", sequenceName = "PAYORDER_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NAME_SEQUENCE")
	private Long id;
	
	@Column
	private String mciAddId;
	
	@Column
	private Double totalPremium;
	
	@Column
	private Date startDate;
	
	@Column
	private String pay;
	
	@Column
	private String policyNumber;
	
	@Column
	private String policyStatusId;
	
	@Column
	private String policyStatusName;
}