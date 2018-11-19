package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the CONTACT database table.
 * 
 */
@Getter
@Setter
public class ContactUpdateVM extends ContactCreateVM {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@ApiModelProperty(value = "ID khách hàng/Contact id", required = true)
	private String contactId;

	private String contactCode;
}