/* 
* Copyright 2011 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
*/
package com.baoviet.agency.bean;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/** 
* 
* @author: Nam, Nguyen Hoai 
*/
@Getter
@Setter
public class ReceiverUserInfoDTO { 

	@NotNull
    @ApiModelProperty(value = "Họ và tên người", required = true)
	private String name; // get; set; } 
	
	@NotNull
    @ApiModelProperty(value = "Địa chỉ người", required = true)
	private String address; // get; set; } 
	
	@NotNull
	@Email
    @ApiModelProperty(value = "Email", required = true)
	private String email ; // get; set; } 
	
	@NotNull
    @ApiModelProperty(value = "Số điện thoại liên hệ", required = true)
	private String mobile; // get; set; }  
	
}