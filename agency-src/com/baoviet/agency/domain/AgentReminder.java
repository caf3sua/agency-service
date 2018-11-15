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
 * The persistent class for the AGENT_REMINDER database table.
 * 
 */
@Entity
@Table(name="AGENT_REMINDER")
@Getter
@Setter
public class AgentReminder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "AGENT_REMINDER_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;
	
	@Column
	private String contactId;
	
	@Column
	private String type;
	
	@Column
	private String productCode;
	
	@Column
	private String content;
	
	@Temporal(TemporalType.DATE)
	@Column
	private Date createdDate;
	
	@Temporal(TemporalType.DATE)
	@Column
	private Date remindeDate;
	
	@Column
	private String note;
	
	@Column
	private String active;
	
}