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
public class PremiumTviVM {

	@NotEmpty
	@ApiModelProperty(value = "Gói bảo hiểm/Insurance package", allowableValues = "1,3", required = true)
	private String premiumPackage;
	
	@NotNull
	@ApiModelProperty(value = "Số người tham gia/Number of person", allowableValues = "1,2,3", required = true)
    private Integer numberOfPerson;
	
	@NotNull
	@ApiModelProperty(value = "Số ngày/Number of day", required = true)
    private Integer numberOfDay;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày bắt đầu/Inception date", allowableValues = "dd/MM/yyyy", required = true)
    private String inceptionDate;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày kết thúc/Expired date", allowableValues = "dd/MM/yyyy", required = true)
    private String expiredDate;

	@NotEmpty
	@ApiModelProperty(value = "Gói tham gia/Insurance plan", allowableValues = "1,2,3,4,5", required = true)
    private String planId;
	
    @NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm/Premium", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumNet;     
	
    @NotNull
	@ApiModelProperty(value = "Phẩn trăm phí giảm/Premium percent discount", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumPercentDiscount;
	
    @NotNull
	@ApiModelProperty(value = "Phí giảm/Premium discount", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumDiscount;
	
    @NotNull
	@ApiModelProperty(value = "Tổng phí bảo hiểm/Total premium", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumTvi;
}