package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Base View Model object for storing query informations.
 * 
 * @author Nam, Nguyen Hoai
 */
@Getter
@Setter
public class AgencyChangePasswordVM {
	
	@NotEmpty
    @ApiModelProperty(value = "Mật khẩu cũ", required = true)
	private String oldPassword;
	
	@NotEmpty
    @ApiModelProperty(value = "Mật khẩu mới", required = true)
	private String newPassword;
	
	@ApiModelProperty(value = "Kết quả cập nhật mật khẩu")
	private boolean result;
}
