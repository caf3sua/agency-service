package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the PaymentMsbDTO database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class PaymentMsbDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String transactionId;
	
    private String gycbhNumber;
    
    public Double premium;
    
    public Integer feeReceive;

}