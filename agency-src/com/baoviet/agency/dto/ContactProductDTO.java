package com.baoviet.agency.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the ContactProduct database table.
 * 
 */
@Getter
@Setter
public class ContactProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String contactId;
	
	private String productCode;
	
	private String productName;
}