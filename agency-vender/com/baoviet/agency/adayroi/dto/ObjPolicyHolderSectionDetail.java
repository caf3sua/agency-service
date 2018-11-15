package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjPolicyHolderSectionDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private ObjectDTO phName;
	
	private ObjectDTO phDOB;
	
	private ObjectDTO phId;
	
	private ObjectDTO phGender;
	
	private ObjectDTO phHomePhone;
	
	private ObjectDTO phCellPhone;
	
	private ObjectDTO phEmail;
	
	private ObjectDTO phPermanentAddress;
	
	private ObjectDTO phMailingAddress;
}
