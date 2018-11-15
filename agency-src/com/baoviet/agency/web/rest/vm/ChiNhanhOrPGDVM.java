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
public class ChiNhanhOrPGDVM {

	@ApiModelProperty(value = "Id Phòng Giao Dịch")
	private String parentId;
	
	@ApiModelProperty(value = "Kiểu phòng giao dịch là ngân hàng (1) hoặc chi nhánh (2)")
	private String type;
}
