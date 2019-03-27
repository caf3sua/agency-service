package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TvcPlaneAddBaseVM {

	@NotEmpty
	@ApiModelProperty(value = "Tên người được bảo hiểm/Insured name", required = true)
	private String insuredName;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày sinh người được BH/Date of birth", allowableValues = "dd/MM/yyyy", required = true)
    private String dob;
	
	@NotEmpty
	@ApiModelProperty(value = "ID/Hộ chiếu người được BH/Passport", required = true)
    private String idPasswport;
	
	@NotEmpty
	@ApiModelProperty(value = "Giới tính 1: Nam, 2: Nữ/Sex 1: Male, 2: Female", allowableValues = "1,2", required = true)
    private String sexId;
    
}
