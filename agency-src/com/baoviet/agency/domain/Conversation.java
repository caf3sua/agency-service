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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the FILES database table.
 * 
 */
@Entity
@Table(name="CONVERSATION")
@Getter
@Setter
public class Conversation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "CONVERSATION_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="CONVERSATION_ID", unique=true)
	private String conversationId;

	@Column
	private String conversationContent;

	@Column
	private String parrentId;

	@Column
	private String lineId;

	@Temporal(TemporalType.DATE)
	@Column
	private Date sendDate;

	@Temporal(TemporalType.DATE)
	@Column
	private Date responseDate;

	@Column
	private String userId;

	@Column
	private String userName;

	@Column
	private String role;

	@Column
	private String sendEmail;

	@Lob
	private byte[] conversationBlob;

	@Column
	private String mailStatus;
	
	@Column
	private String title;
	
	@Column
	private Date createDate;

}