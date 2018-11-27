package com.baoviet.agency.web.rest.vm;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.baoviet.agency.web.rest.vm.common.PageableVM;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the CONTACT database table.
 * 
 */
@Getter
@Setter
public class ContactSearchVM implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Tên khách hàng/Contact name", required = true)
	private String contactName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày sinh/Date of birth", allowableValues = "dd/MM/yyyy", required = false)
	private Date dateOfBirth;

	@ApiModelProperty(value = "Số điện thoại/Phone", required = true)
	private String phone;

	@ApiModelProperty(value = "Số hộ chiếu-CMND/Passport", required = false)
	private String idNumber;
	
	@ApiModelProperty(value = "Loại khách hàng", required = false, allowableValues = "POTENTIAL,FAMILIAR,VIP")
	private String groupType;
	
	@ApiModelProperty(value = "Khách hàng là cá nhân/tổ chức", required = false, allowableValues = "PERSON,CATEGORY")
	private String categoryType;
	
	private PageableVM pageable;
} 