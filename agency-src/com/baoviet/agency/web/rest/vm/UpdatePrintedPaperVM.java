package com.baoviet.agency.web.rest.vm;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the CONTACT database table.
 * 
 */
@Getter
@Setter
public class UpdatePrintedPaperVM implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Ấn chỉ sử dụng")
	private String printedPaperUse;
}