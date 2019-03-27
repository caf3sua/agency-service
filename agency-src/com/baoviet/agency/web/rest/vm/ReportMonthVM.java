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
public class ReportMonthVM {

	@ApiModelProperty(value = "Ngân hàng")
	private String nganHang;
	
	@ApiModelProperty(value = "Chi nhánh")
	private String chiNhanh;
	
	@ApiModelProperty(value = "Phòng giao dịch")
	private String phongGd;
	
	@ApiModelProperty(value = "Tháng")
	private Integer thang;
	
	@ApiModelProperty(value = "Năm")
	private Integer nam;
}
