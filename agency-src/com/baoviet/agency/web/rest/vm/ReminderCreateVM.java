package com.baoviet.agency.web.rest.vm;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the ReminderCreateVM database table.
 * 
 */
@Getter
@Setter
public class ReminderCreateVM implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@ApiModelProperty(value = "Id khách hàng", required = true)
	private String contactId;
	
	@NotEmpty
	@ApiModelProperty(value = "Mã sản phẩm bảo hiểm cần nhắc nhở", required = true)
	private String productCode;
	
	@NotEmpty
	@ApiModelProperty(value = "Nội dung thông báo", required = true)
	private String content;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày thông báo", allowableValues = "dd/MM/yyyy", required = true)
	private String remindeDate;
	
	@ApiModelProperty(value = "Ghi chú")
	private String note;

	@ApiModelProperty(value = "Id nhắc nhở - Dùng trong trường hợp update")
	private String id;
}