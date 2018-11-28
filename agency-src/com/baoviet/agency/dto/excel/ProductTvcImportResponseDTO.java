package com.baoviet.agency.dto.excel;

import java.io.Serializable;
import java.util.List;

import com.baoviet.agency.web.rest.vm.TvcAddBaseVM;
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
public class ProductTvcImportResponseDTO extends BasePathInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Danh sách người được bảo hiểm/List of the insured")
    private List<TvcAddBaseVM> listTvcAddBaseVM;
}