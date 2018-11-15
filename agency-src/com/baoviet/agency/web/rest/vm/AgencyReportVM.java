package com.baoviet.agency.web.rest.vm;

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
public class AgencyReportVM extends AgencyBaseVM {

	@ApiModelProperty(value = "Ngân hàng")
	private String nganHang;
	
	@ApiModelProperty(value = "Chi nhánh")
	private String chiNhanh;
	
	@ApiModelProperty(value = "Phòng giao dịch")
	private String phongGd;
	
	@ApiModelProperty(value = "Mã đại lý")
	private String madaily;
			
	@ApiModelProperty(value = "Ngày yêu cầu từ")
	private String ngayyctu;
	
	@ApiModelProperty(value = "Ngày yêu cầu đến")
	private String ngayycden;
	
	private String ngayhltu;
	private String ngayhlden;
	
	private String ngaydptu;
	private String ngaydpden;
	
}
