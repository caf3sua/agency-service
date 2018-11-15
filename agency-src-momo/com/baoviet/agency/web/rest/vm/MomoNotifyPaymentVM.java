package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class MomoNotifyPaymentVM {

	@NotEmpty
    private String contactCode;

	@NotEmpty
    private String type;
}
