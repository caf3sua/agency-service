package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectJsonBVP implements Serializable {
	private static final long serialVersionUID = 1L;

	private ObjPolicy policy;
	
	private ObjPolicyHolder policyHolder;
	
	private ObjInsuredPerson insuredPerson;
	
	private ObjBeneficiary beneficiary;
	
	private ObjQuestions questions;
	
	private ObjCommitment commitment;
}
