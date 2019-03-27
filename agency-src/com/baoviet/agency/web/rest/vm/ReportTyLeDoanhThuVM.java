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
public class ReportTyLeDoanhThuVM {

	@ApiModelProperty(value = "Mã ngân hàng")
	private String maNganHang;
	
	@ApiModelProperty(value = "Mã chi nhánh")
	private String maChiNhanh;
	
	@ApiModelProperty(value = "Mã phòng giao dịch")
	private String maPGD;
	
	@ApiModelProperty(value = "Năm")
	private Integer nam;
}
