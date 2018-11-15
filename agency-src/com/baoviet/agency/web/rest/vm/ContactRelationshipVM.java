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
public class ContactRelationshipVM implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Mã quan hệ", allowableValues = "30,31,32,33,34,35,36,37,38,41")
	private String relationId;

	@ApiModelProperty(value = "Id người quan hệ")
	private String contactRelationId;

	@ApiModelProperty(value = "Tên người quan hệ")
	private String contactRelationName;
	
	@ApiModelProperty(value = "Tên quan hệ - Không bắt buộc nhập")
	private String relationName;

}