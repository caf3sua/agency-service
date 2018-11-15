package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENCY_RELATION database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class AgencyRelationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;
	
	private String address;
	
	private String phone;
	
	private String fax;
	
	private String email;
	
	private String bankAccount;
	
	private String bankName;
	
	private String bankBranch;
	
	private String taxNumber;
	
	private String fs1;
	
	private String fs2;
	
	private String fs3;
	
	private String fs4;
	
	private String fs5;
	
	private String fs6;
	
	private String fs7;
	
	private String fs8;
	
	private String fs9;
	
	private String fs10;
	
	private String parrenetId;
	
	private String type;
	
	private String fs11;
	
	private String fs12;
	
	private String fs13;
	
	private String fs14;
	
	private String fs15;
	
	private String idAddress;
}