package com.baoviet.agency.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENT_DISCOUNT database table.
 * 
 */
@Getter
@Setter
public class AgentDiscountDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String agencyId;
	
	private Double discount;
	
	private String lineId;
	
	private String description;
}