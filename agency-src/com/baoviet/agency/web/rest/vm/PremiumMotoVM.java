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
public class PremiumMotoVM {

	@NotEmpty
    @ApiModelProperty(value = "Nhóm loại xe", allowableValues = "1, 2", required = true)
	private String typeOfMoto; //": "PGM1",
	
	@NotNull
    @ApiModelProperty(value = "Bảo hiểm trách nhiệm dân sự bắt buộc", allowableValues = "true, false", required = true)
	private Boolean tndsbbCheck;
	
    @ApiModelProperty(value = "Phí bảo hiểm trách nhiệm dân sự bắt buộc bao gồm VAT")
    @JsonSerialize(using = DoubleSerializer.class)
    private double tndsbbPhi;

	@NotNull
    @ApiModelProperty(value = "Bảo hiểm trách nhiệm dân sự tự nguyện", allowableValues = "true, false", required = true)
    private Boolean tndstnCheck;
	
    @ApiModelProperty(value = "Số tiền tham gia bảo hiểm", allowableValues = "50000000, 100000000")
    @JsonSerialize(using = DoubleSerializer.class)
    private double tndstnSotien;
	
    @ApiModelProperty(value = "Phí bảo hiểm trách nhiệm dân sự tự nguyện  bao gồm VAT")
    @JsonSerialize(using = DoubleSerializer.class)
    private double tndstnPhi;

	@NotNull
    @ApiModelProperty(value = "Bảo hiểm người ngồi trên xe", allowableValues = "true, false", required = true)
    private Boolean nntxCheck;
	
    @ApiModelProperty(value = "Số tiền bảo hiểm NNTX")
    @JsonSerialize(using = DoubleSerializer.class)
    private double nntxStbh;
	
    @ApiModelProperty(value = "Số người ngồi trên xe")
    private Integer nntxSoNguoi;
	
    @ApiModelProperty(value = "Phí đóng bảo hiểm người ngồi trên xe")
    @JsonSerialize(using = DoubleSerializer.class)
    private double nntxPhi;

	@NotNull
    @ApiModelProperty(value = "Bảo hiểm cháy nổ", allowableValues = "true, false", required = true)
    private Boolean chaynoCheck;

    @ApiModelProperty(value = "Số tiền bảo hiểm cháy nổ")
    @JsonSerialize(using = DoubleSerializer.class)
    private double chaynoStbh;

    @ApiModelProperty(value = "Phí bảo hiểm cháy nổ  bao gồm VAT")
    @JsonSerialize(using = DoubleSerializer.class)
	private double chaynoPhi;
	
    @ApiModelProperty(value = "Tổng phí bảo hiểm bao gồm VAT")
    @JsonSerialize(using = DoubleSerializer.class)
    private double tongPhi;
	
}
