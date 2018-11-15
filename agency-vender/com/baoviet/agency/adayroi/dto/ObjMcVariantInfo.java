package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjMcVariantInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<McVariantInfoDTO> variantInfo;
}
