package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class AgencyLoginVM extends AgencyBaseVM {

	@NotEmpty
    @ApiModelProperty(allowableValues = "D000061547@baoviet.com.vn")
    private String username;

	@NotEmpty
    @ApiModelProperty(allowableValues = "123456")
    private String password;
	
	@ApiModelProperty(value = "Case : admin login")
	private Boolean isAdmin;
}
