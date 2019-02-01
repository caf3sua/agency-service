package com.baoviet.agency.dto.excel;

import java.io.Serializable;
import java.util.List;

import com.baoviet.agency.dto.report.BcKhaiThacMotoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductMotoExcelDTO extends BasePathInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Danh sách đơn")
    private List<BcKhaiThacMotoDTO> data;
}