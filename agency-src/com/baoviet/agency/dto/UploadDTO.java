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
 * The persistent class for the TL_ADD database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class UploadDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String fileName;
	
	private String fileNameEdit;
	
	private String path;
	
	private Integer status;
	
	private Integer recordSuss;
	
	private Integer recordError;
	
	private Integer sendEmailConfirm;
	
	private String fileNameError;
	
	private String userImport;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date bvmvsSysdate;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date uploadDate;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date sendEmailDate;
	
	private Integer type;
	
	private String companyname;
	
	private String departmentname;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date processDate;
}