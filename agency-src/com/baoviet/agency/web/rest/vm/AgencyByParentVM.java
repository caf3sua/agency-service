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
public class AgencyByParentVM {

	@ApiModelProperty(value = "Id Phòng Giao Dịch")
	private String pgdId;
	
}
