package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value="PremiumKcareVM", description="Premium kcare model for calculate premium")
public class PremiumKcareVM {

	@NotEmpty
    @ApiModelProperty(value = "Chương trình bảo hiểm", allowableValues = "PGM1,PGM2,PGM3", required = true)
	private String typeOfKcare; //": "PGM1",
	
	@NotEmpty
    @ApiModelProperty(value = "Ngày sinh", allowableValues = "dd/MM/yyyy", required = true)
	private String ngaySinh; //": "19/01/2000",
	
	@NotEmpty
    @ApiModelProperty(value = "Thời hạn bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
	private String ngayBatDau; //": "22/01/2018",
	
	@NotEmpty
    @ApiModelProperty(value = "Giới tính (1: Nam, 2: Nữ)", allowableValues = "1,2", required = true)
	private String gioiTinh; //": "1",2
	
	@ApiModelProperty(value = "Phí bảo hiểm sau khi giảm", allowableValues = "0")
	@JsonSerialize(using = DoubleSerializer.class)
	private double premiumKCare; //": 160875,
	
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm", allowableValues = "0")
	@JsonSerialize(using = DoubleSerializer.class)
	private double premiumNet; //": 160875,
	
	@ApiModelProperty(value = "Phần trăm giảm phí", allowableValues = "0")
	@JsonSerialize(using = DoubleSerializer.class)
	private double premiumDiscount; //": 0
}
