package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class McVariantInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String variantCategoryID;

	private String variantCategoryName;

	private String variantCategoryValueID;

	private String variantValueCategoryName;
	
}
