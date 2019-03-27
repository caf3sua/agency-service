package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * HomeBase
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class OrderAdminVM {

	@NotEmpty
	@ApiModelProperty(value = "Số hợp đồng", required = true)
	private String gycbhNumber;
	
	@NotEmpty
	@ApiModelProperty(value = "Nội dung chi tiết", required = true)
    private String conversationContent;
	
}
