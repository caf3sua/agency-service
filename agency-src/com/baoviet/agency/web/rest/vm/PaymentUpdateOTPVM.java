package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * Tư vấn viên
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class PaymentUpdateOTPVM {

	@NotEmpty
    private String otp;
    
	@NotEmpty
    private String gycbhNumber;
    
    private boolean result; 
    
}
