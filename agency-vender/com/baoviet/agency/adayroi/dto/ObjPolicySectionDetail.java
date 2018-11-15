package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjPolicySectionDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private ObjectDTO startDate;
	
	private ObjectDTO endDate;
	
	private ObjectDTO duration;
}
