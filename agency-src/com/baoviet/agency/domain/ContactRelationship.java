package com.baoviet.agency.domain;

import java.io.Serializable;

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
 * The persistent class for the CUSTOMER_RELATIONSHIP database table.
 * 
 */
@Entity
@Table(name="AGENT_CONTACT_RELATIONSHIP")
@Getter
@Setter
public class ContactRelationship implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "AGENT_CONTACT_RELATIONSHIP_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ID", unique=true)
	private String id;
	
	@Column
	private String contactId;
	
	@Column
	private String contactName;
	
	@Column
	private String relationId;
	
	@Column
	private String relationName;
	
	@Column
	private String contactRelationId;
	
	@Column
	private String contactRelationName;
}