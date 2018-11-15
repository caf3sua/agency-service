package com.baoviet.agency.web.rest.vm;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Base View Model object for storing query informations.
 * 
 * @author Nam, Nguyen Hoai
 */
@Getter
@Setter
public class NotifyPaymentVM implements Serializable {
	private static final long serialVersionUID = 1L;

	// required
	@NotEmpty
	@ApiModelProperty(value = "Số hợp đồng bảo hiểm", required = true)
	private String gycbhNumber;
	
	// 1: success, 0: fail
	@NotEmpty
	@ApiModelProperty(value = "Trạng thái bảo hiểm 1: success, 0: fail", allowableValues = "0,1", required = true)
	private String statusPayment;
	
	@ApiModelProperty(value = "Thông báo lỗi")
	private String message;

	// 1: success, 0: fail
	@ApiModelProperty(value = "Kết quả trả về 1: success, 0: fail")
	private String result;
}
