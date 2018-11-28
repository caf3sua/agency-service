package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TvcAddBaseVM {

	@NotEmpty
	@ApiModelProperty(value = "Tên người được bảo hiểm/Insured name", required = true)
	private String insuredName;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày sinh người được BH/Date of birth", allowableValues = "dd/MM/yyyy", required = true)
    private String dob;
	
	@NotEmpty
	@ApiModelProperty(value = "Hộ chiếu người được BH/Passport", required = true)
    private String idPasswport;
	
	@NotEmpty
	@ApiModelProperty(value = "Quan hệ người được bảo hiểm với người YCBH/Relationship", allowableValues = "30, 31, 32, 33, 34, 39", required = true)
    private String relationship;
	
	private int order;
}
