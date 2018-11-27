package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENT_DISCOUNT database table.
 * 
 */
@Entity
@Table(name="AGENT_DISCOUNT")
@Getter
@Setter
public class AgentDiscount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	private String id;
	
	@Column
	private String agencyId;
	
	@Column
	private Double discount;
	
	@Column
	private String lineId;
	
	@Column
	private String description;
}