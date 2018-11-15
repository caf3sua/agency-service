package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjInsuredPersonSectionDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private ObjectDTO ipName;
	
	private ObjectDTO ipDOB;
	
	private ObjectDTO ipBirthCertificate;
	
	private ObjectDTO ipBirthCertificateUrl;
	
	private ObjectDTO ipId;
	
	private ObjectDTO ipRelationWithPh;
	
	private ObjectDTO ipGender;
	
	private ObjectDTO ipHeight;
	
	private ObjectDTO ipWeight;
	
	private ObjectDTO ipOccupation;
	
	private ObjectDTO ipPermanentNation;
	
	private ObjectDTO ipBirthPlace;
	
	private ObjectDTO insuranceRejectionInPast;
	
	private ObjectDTO insuranceRejectionReason;
}
