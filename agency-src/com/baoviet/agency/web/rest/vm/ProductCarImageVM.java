package com.baoviet.agency.web.rest.vm;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.bean.FileContentDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCarImageVM {

	@NotEmpty
	@ApiModelProperty(value = "Số GYCBH", required = true)
    private String gycbhNumber;

    @ApiModelProperty(value = "data content của Góc phải đầu xe")
    private FileContentDTO imgPDau;
    
    @ApiModelProperty(value = "data content của Góc trái đầu xe")
    private FileContentDTO imgTDau;
    
    @ApiModelProperty(value = "data content của Góc phải đuôi xe")
    private FileContentDTO imgPDuoi;
    
    @ApiModelProperty(value = "data content của Góc trái đuôi xe")
    private FileContentDTO imgTDuoi;
    
    @ApiModelProperty(value = "data content của Đăng kiểm")
    private FileContentDTO imgDKiem;
}

