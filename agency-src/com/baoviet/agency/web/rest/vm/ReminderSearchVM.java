package com.baoviet.agency.web.rest.vm;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
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
public class ReminderSearchVM implements Serializable {
	private static final long serialVersionUID = 1L;

	private String reminderId;
	
	private String active;
	
	@ApiModelProperty(value = "Id khách hàng")
	private String contactId;
	
	@ApiModelProperty(value = "Mã sản phẩm bảo hiểm")
	private String productCode;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Từ ngày", allowableValues = "dd/MM/yyyy", required = false)
	private Date fromDate;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Đến ngày", allowableValues = "dd/MM/yyyy", required = false)
	private Date toDate;

}