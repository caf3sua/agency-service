package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumMotoHondaVM {

	@NotEmpty
    @ApiModelProperty(value = "Năm sử dụng", allowableValues = "0, 1, 2, 3", required = true)
	private String namSd;
	
//	@NotEmpty
//    @ApiModelProperty(value = "Gói tham gia", allowableValues = "1, 2, 3", required = true)
	private String goi;
	
	@NotEmpty
    @ApiModelProperty(value = "id model xe", required = true)
	private String idModel;
	
	@ApiModelProperty(value = "Số tiền tham gia bảo hiểm")
	private double soTienBh;
	
    @ApiModelProperty(value = "Tổng phí gói 1 bảo hiểm bao gồm VAT")
    @JsonSerialize(using = DoubleSerializer.class)
    private double tongPhi1;
    
    @ApiModelProperty(value = "Tổng phí gói 2 bảo hiểm bao gồm VAT")
    @JsonSerialize(using = DoubleSerializer.class)
    private double tongPhi2;
    
    @ApiModelProperty(value = "Tổng phí gói 3 bảo hiểm bao gồm VAT")
    @JsonSerialize(using = DoubleSerializer.class)
    private double tongPhi3;
	
}
