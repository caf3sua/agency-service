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
public class ContactProductVM implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Mã sản phẩm bảo hiểm", allowableValues = "CAR,MOTO,HOME,KCARE,BVP,KHC,TNC,TVC,TVI")
	private String productCode;

	@ApiModelProperty(value = "Tên sản phẩm bảo hiểm")
	private String productName;

}