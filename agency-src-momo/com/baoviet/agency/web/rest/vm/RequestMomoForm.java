package com.baoviet.agency.web.rest.vm;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.dto.momo.component.MomoComponent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class RequestMomoForm {

	@NotEmpty
	@ApiModelProperty(value = "MoMo provide for partner", required = true)
    private String partnerCode;

	@NotEmpty
	@ApiModelProperty(value = "Indentify for each service of partner", required = true)
    private String serviceId;
	
	@NotEmpty
	@ApiModelProperty(value = "Unique for each flow payment like session id of MoMo’s user", required = true)
    private String requestId;
	
	@NotEmpty
	@ApiModelProperty(value = "Define type of request", required = true, allowableValues = "getForm,submit")
    private String requestType;
	
	@NotEmpty
	@ApiModelProperty(value = "Current step", required = true, allowableValues = "1,2")
    private String currentStep;
	
	@ApiModelProperty(value = "MoMo will attach value for each field name")
	private List<MomoComponent> formData;
}
