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
public class ReportSearchCriterialVM {

	@ApiModelProperty(value = "Từ ngày", allowableValues = "dd/MM/yyyy")
	private String fromDate;
	
	@ApiModelProperty(value = "Đến ngày", allowableValues = "dd/MM/yyyy")
	private String toDate;
	
	@ApiModelProperty(value = "Khoảng thời gian", allowableValues = "WEEK,MONTH,YEAR")
	public String periodTime;	
}
