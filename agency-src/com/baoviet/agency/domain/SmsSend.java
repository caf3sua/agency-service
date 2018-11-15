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
 * The persistent class for the SMS_SEND database table.
 * 
 */
@Entity
@Table(name="SMS_SEND")
@Getter
@Setter
public class SmsSend implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "SMS_SEND_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="SMS_ID", unique=true)
	private String smsId;
	
	@Column
	private String userId;
	
	@Column
	private String userName;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String content;
	
	@Column
	private Date smsSysdate;
	
	@Column
	private String filename;
	
	@Column
	private Integer numberSuccess;
	
	@Column
	private Integer numberFails;
	
	@Column
	private String fullname;
	
	@Column
	private String companyid;
	
	@Column
	private String departmentid;
	
	@Column
	private String companyname;
	
	@Column
	private String departmentname;
}