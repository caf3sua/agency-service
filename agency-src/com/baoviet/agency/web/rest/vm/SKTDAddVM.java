package com.baoviet.agency.web.rest.vm;


import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class SKTDAddVM {

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày điều trị", allowableValues = "dd/MM/yyyy")
	private Date ngaydieutri ;
	
	@ApiModelProperty(value = "Chuẩn đoán")
    private String chuandoan ;
	
	@ApiModelProperty(value = "Chi tiết điều trị")
    private String chitietdieutri ;
	
	@ApiModelProperty(value = "Kết quả")
    private String ketqua ;
	
	@ApiModelProperty(value = "Tên, địa chỉ bác sĩ/bệnh viện")
    private String benhvienorbacsy ;
}
