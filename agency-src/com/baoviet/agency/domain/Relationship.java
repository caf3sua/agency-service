package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the PA_RATE database table.
 * 
 */
@Entity
@Table(name="RELATIONSHIP")
@Getter
@Setter
public class Relationship implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RELATIONSHIP_ID", unique=true)
	private String relationshipId;
	
	@Column
	private String relationshipName;
	
}