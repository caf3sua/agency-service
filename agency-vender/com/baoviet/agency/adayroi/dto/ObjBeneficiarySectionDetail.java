package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjBeneficiarySectionDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private ObjectDTO bnName;
	
	private ObjectDTO bnId;
	
	private ObjectDTO bnRelationWithIp;
	
	private ObjectDTO bnPhoneNumber;
	
	private ObjectDTO bnAddress;
}
