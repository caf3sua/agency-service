package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumTvcPlaneVM {

	@NotEmpty
	@ApiModelProperty(value = "Chặng bay 1: Nội địa, 2: Quốc tế/Flight 1: Domestic, 2: International", allowableValues = "1,2", required = true)
	private String areaId;

	@NotEmpty
	@ApiModelProperty(value = "Kiểu bay 1: Một chiều, 2: Khứ hồi/Flight type 1: One way, 2: Return", allowableValues = "1,2", required = true)
	private String planId;

	@NotEmpty
	@ApiModelProperty(value = "Ngày khởi hành/Inception Date", allowableValues = "dd/MM/yyyy", required = true)
	private String dateFrom;

	@ApiModelProperty(value = "Ngày về/Expired Date", allowableValues = "dd/MM/yyyy")
	private String dateTo;

	@NotEmpty
	@ApiModelProperty(value = "Loại đối tác Bảo Việt/Type Of Agency", allowableValues = "GOTADI", required = true)
	private String typeOfAgency;

	@ApiModelProperty(value = "Phí bảo hiểm trả về/Premium")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double premium;
}