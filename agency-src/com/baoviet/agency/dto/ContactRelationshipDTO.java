package com.baoviet.agency.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the CUSTOMER_RELATIONSHIP database table.
 * 
 */
@Getter
@Setter
public class ContactRelationshipDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String contactId;

	private String contactName;

	private String relationId;

	private String relationName;

	private String contactRelationId;

	private String contactRelationName;
}