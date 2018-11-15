package com.baoviet.agency.dto.momo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class PaymentInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/* Amount of order  */
	private String amount;
	
	/* Order id of merchant  */
	private String orderId;
	
	/* Order information  */
	private String description;
	
	/* Extra data  */
	private ExtraDataDTO extraData;

}
