/* 
* Copyright 2011 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
*/
package com.baoviet.agency.bean;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/** 
* 
* @author: Nam, Nguyen Hoai 
*/
@Getter
@Setter
public class TncAddDTO { 

	@NotNull
	@ApiModelProperty(value = "Tên người được bảo hiểm", required = true)
	private String insuredName;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull
	@ApiModelProperty(value = "Ngày sinh người được bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
	private Date dob;
	
	@NotNull
	@ApiModelProperty(value = "Số CMT/Passport", required = true)
	private String idPasswport;
	
	@NotNull
	@ApiModelProperty(value = "Giới tính", required = true)
	private String title;
}