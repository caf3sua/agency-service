package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String viName;
	
	private String value;
	
	private String type;
}
