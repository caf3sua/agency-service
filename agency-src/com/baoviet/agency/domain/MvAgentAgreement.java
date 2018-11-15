package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MV_AGENT_AGREEMENT database table.
 * 
 */
@Entity
@Table(name="MV_AGENT_AGREEMENT")
@Getter
@Setter
public class MvAgentAgreement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private String agentUrn;
	
	@Column
	private String agentCode;
	
	@Column
	private String agentName;
	
	@Column
	private Integer agreementStatus;
	
	@Column
	private String agreementName;
	
	@Column
	private String departmentUrn;
	
	@Column
	private String departmentName;
	
	@Column
	private Date createdDate;
	
	@Column
	private String createdBy;
	
	@Column
	private Date modifiedDate;
	
	@Column
	private String modifiedBy;
	
	@Column
	private String departmentCode;
	
	@Column
	private Double commissionRate;
	
	@Column
	private Double agreementUrn;
	
	@Column
	private Date terminatedDate;
}