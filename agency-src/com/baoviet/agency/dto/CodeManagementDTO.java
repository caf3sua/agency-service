package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the CODE_MANAGEMENT database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class CodeManagementDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date bvmvsSysdate;

	private String codeType;

	private String companyCode;

	private String companyId;

	private String deparmentId;

	private String departmentCode;

	private String idNumber;

	private String issueNumber;

	private String type;

	private String year;
}