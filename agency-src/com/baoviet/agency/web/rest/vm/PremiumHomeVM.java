package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumHomeVM {

	// thoi han bao hiem
	@NotEmpty
	@ApiModelProperty(value = "Gói bảo hiểm", allowableValues = "1,2,3", required = true)
    private String yearBuildCode;

    // gioi han boi thuong
	//@NotNull
//	@NotNull
//	@Size(min = 1)
	@NotEmpty
	@ApiModelProperty(value = "Giới hạn bồi thường tài sản", required = true)
    private String si;

    // gioi han boi thuong tai san ben trong
	@ApiModelProperty(value = "Giới hạn bồi thường tài sản bên trong ngôi nhà", allowableValues = "100000000,300000000,500000000,750000000,1000000000")
    private String siin;

    // phi bao hiem phan ngoi nha
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumsi;

	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumsiin;

    // tong phi
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm sau khi giảm", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumHome;
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumNet;
	
	@NotNull
	@ApiModelProperty(value = "Phần trăm giảm phí", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumDiscount;
    
}