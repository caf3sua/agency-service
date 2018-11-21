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
 * The persistent class for the ATTACHMENT database table.
 * 
 */
@Entity
@Table(name="ATTACHMENT")
@Getter
@Setter
public class Attachment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "ATTACHMENT_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ATTACHMENT_ID", unique=true)
	private String attachmentId;
	
	@Lob
	@Column
	private byte[] content;
	
	@Column
	private String parrentId;
	
	@Column
	private Double contentLeng;
	
	@Column
	private String attachmentName;
	
	@Column
	private Date createDate;
	
	@Column
	private Date modifyDate;
	
	@Column
	private String userId;
	
	@Column
	private Date tradeolSysdate;
	
	@Column
	private Integer istransferred;
	
	@Column
	private String attachmentType;
}