package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENT_DOCUMENT database table.
 * 
 */
@Entity
@Table(name="AGENT_DOCUMENT")
@Getter
@Setter
public class AgentDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "FILES_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ID", unique=true)
	private String id;
	
	@Column
	private String code;

	@Column
	private String name;
	
	@Column
	private String fileName;
	
	@Lob
	private byte[] content;

	@Column
	private String type;
	
	@Column
	private String note;
	
	@Column
	private Long numberDownload;
	
	@Column
	private String usernameUpload;
	
	@Column
	private Date dateUpload;
}