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
public class ReportBchhVM {

	@ApiModelProperty(value = "Ngân hàng")
	private String nganHang;
	
	@ApiModelProperty(value = "Chi nhánh")
	private String chiNhanh;
	
	@ApiModelProperty(value = "Phòng giao dịch")
	private String phongGd;
	
	@ApiModelProperty(value = "Mã đại lý")
	private String madaily;
	
	@ApiModelProperty(value = "Tháng")
	private String thang;
	
	@ApiModelProperty(value = "Năm")
	private String nam;
}
