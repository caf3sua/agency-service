package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjMemoResponses implements Serializable {
	private static final long serialVersionUID = 1L;

	private ObjectDTO dateoftreatment;
	
	private ObjectDTO diagnosis;
	
	private ObjectDTO treatment;
	
	private ObjectDTO result;
	
	private ObjectDTO doctor;
	
	private ObjectDTO medicalconditions;
	
	private ObjectDTO treatmentstatus;
	
	private ObjectDTO counselling;
	
	private ObjectDTO currentstatus;
	
	private ObjectDTO textResponse;
}

