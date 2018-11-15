package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjPolicyHolder implements Serializable {
	private static final long serialVersionUID = 1L;

	private String sectionName;
	
	private ObjPolicyHolderSectionDetail sectionDetail;
}
