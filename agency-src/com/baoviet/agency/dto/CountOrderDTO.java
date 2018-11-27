package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the DepartmentDTO
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class CountOrderDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long countCart;

	private Long countBvWaiting;
	
	private Long countAgencyWaiting;
	
	private Long countOrderMe;
	
	private Long countOrderLater;
	
	private Long countOrderDebit;
	
	private Long countOrderExpire;
	
	private Long countOrderOther;
	
	private Long countOrderTrans;
}