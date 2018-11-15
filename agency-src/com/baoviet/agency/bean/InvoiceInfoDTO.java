/* 
* Copyright 2011 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
*/
package com.baoviet.agency.bean;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author: Nam, Nguyen Hoai
 */
@Getter
@Setter
public class InvoiceInfoDTO {

	@NotNull
	@ApiModelProperty(value = "Kiểm tra có xuất hóa đơn GTGT hay không? 1: Có/ Check 1: Yes, 0: No", allowableValues = "0,1", required = true)
	private String check; // get; set; }

	@ApiModelProperty(value = "Họ và tên người mua/Buyer name")
	private String name; // get; set; }

	@ApiModelProperty(value = "Tên đơn vị/Company name")
	private String company; // get; set; }

	@ApiModelProperty(value = "Mã số thuế/Tax code")
	private String taxNo; // get; set; }

	@ApiModelProperty(value = "Địa chỉ đơn vị/ Address")
	private String address; // get; set; }

	@ApiModelProperty(value = "Số tài khoản/Account code")
	private String accountNo; // get; set; }
}