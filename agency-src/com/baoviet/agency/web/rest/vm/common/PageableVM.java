package com.baoviet.agency.web.rest.vm.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class PageableVM {

	@ApiModelProperty(value = "Page lấy dữ liệu")
    private int page;
    
	@ApiModelProperty(value = "Số lượng record trên mỗi page")
	private int size;
	
	@ApiModelProperty(value = "Sắp xếp, format: id,desc/asc")
	private String sort = "id,asc";
}
