package com.baoviet.agency.web.rest.vm;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.utils.DateSerializer;
import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumTncVM {

	@NotNull
	@ApiModelProperty(value = "Số người tham gia bảo hiểm.", required = true)
	private Integer numberperson;

    // so thang tham gia: default = 12
	@NotNull
	@ApiModelProperty(value = "Số tháng tham gia bảo hiểm.", required = true)
    private int numbermonth;

	@JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull
	@ApiModelProperty(value = "Ngày bắt đầu tham gia bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
	private Date insurancestartdate;

    // so tien goi nao
    @NotNull
	@ApiModelProperty(value = "Số tiền đóng bảo hiểm", allowableValues = "20000000,30000000,40000000,50000000,60000000,70000000,80000000,90000000,100000000", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumPackage;

    @NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumnet;

    @NotNull
	@ApiModelProperty(value = "Phần trăm giảm phí", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumdiscount;

    @NotNull
	@ApiModelProperty(value = "Phí bảo hiểm sau khi giảm", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premiumtnc;
}
