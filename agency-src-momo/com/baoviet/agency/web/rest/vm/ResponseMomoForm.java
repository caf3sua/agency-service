package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.dto.momo.PaymentInfoDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class ResponseMomoForm extends RequestMomoForm {

	@NotEmpty
	@ApiModelProperty(value = "Define type of response", allowableValues = "render,payment")
    private String responseType;
	
	@ApiModelProperty(value = "Contains payment info")
    private PaymentInfoDTO paymentInfo;
}
