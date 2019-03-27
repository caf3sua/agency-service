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
public class SearchPrintedPaperVM implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Loại ấn chỉ")
	private String type;

	@ApiModelProperty(value = "Số URN/Mã vạch")
	private String urn;
	
	@ApiModelProperty(value = "Số ấn chỉ")
	private String number;
	
	@ApiModelProperty(value = "Số đơn bảo hiểm")
	private String gycbhNumber;
}