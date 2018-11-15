package com.baoviet.agency.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * HomeBase
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class ReportToporSaleVM {

	@ApiModelProperty(value = "Tháng")
	private Integer month;
		
	@ApiModelProperty(value = "Năm")
	private Integer year;
}
