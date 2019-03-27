package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.Email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * HomeBase
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class ReportLuyKeTongDoanhThuVM {

	@ApiModelProperty(value = "Email")
	@Email
	private String email;
		
	@ApiModelProperty(value = "Năm")
	private Integer year;
}
