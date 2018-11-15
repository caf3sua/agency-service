package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjQuestions implements Serializable {
	private static final long serialVersionUID = 1L;

	private String sectionName;
	
	private List<ObjQuestionsSectionDetail> sectionDetail;
}
