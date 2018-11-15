package com.baoviet.agency.web.rest.vm;

import java.io.Serializable;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
public class PremiumKhcPersonVM implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Ngày sinh", allowableValues = "dd/MM/yyyy")
    private String dob;

	@ApiModelProperty(value = "Họ tên người được bảo hiểm")
    private String insuredName;

	@ApiModelProperty(value = "Phí bảo hiểm")
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premium;
}