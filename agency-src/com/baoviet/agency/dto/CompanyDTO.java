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
public class CompanyDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String companyId;

	private String companyName;
}