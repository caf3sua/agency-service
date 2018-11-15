package com.baoviet.agency.dto.report;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ReportDataDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long totalIncome;
	
	private long previousIncome;
	
	private long nowIncome;
	
	private long totalCommission;
	
	private long previousCommission;
	
	private long expectCommission;
	
	List<BcAgencyDTO> data;
	
	List<BcAgencyDTO> otherData;
}