package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumCARVM {

	@NotNull
	@ApiModelProperty(value = "Số tiền giảm phí", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double changePremium;

	@NotNull
	@ApiModelProperty(value = "Điều khoản lựa chọn garage sửa chữa", allowableValues = "TRUE,FALSE", required = true)
	private Boolean garage;

	@NotNull
	@ApiModelProperty(value = "Điều khoản không khấu hao thay mới", allowableValues = "TRUE,FALSE", required = true)
	private Boolean khauHao;

	@NotNull
	@ApiModelProperty(value = "Tham gia điều khoản không khấu trừ 500000/vụ", allowableValues = "TRUE,FALSE", required = true)
	private Boolean khauTru;

	@NotNull
	@ApiModelProperty(value = "Điều khoản mất cắp bộ phận", allowableValues = "TRUE,FALSE", required = true)
	private Boolean matCap;

	@NotNull
	@ApiModelProperty(value = "Năm sản xuất/đăng ký xe", required = true)
	private Integer namSX;

	@NotNull
	@ApiModelProperty(value = "Bảo hiểm người ngồi trên xe", allowableValues = "TRUE,FALSE", required = true)
	private Boolean nntxCheck;

	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm người ngồi trên xe ", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double nntxPhi;

	@NotNull
	@ApiModelProperty(value = "Số người ngồi trên xe", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double nntxSoCho;
	
	@NotNull
	@ApiModelProperty(value = "Số tiền bảo hiểm NNTX", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double nntxSoTien;
	
	@NotNull
	@ApiModelProperty(value = "Điều khoản ngập nước", allowableValues = "TRUE,FALSE", required = true)
	private Boolean ngapNuoc;
	
	@NotNull
	@ApiModelProperty(value = "Tổng phí bảo hiểm bao gồm VAT", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double premium;
	
	@NotNull
	@ApiModelProperty(value = "Nhóm loại xe", allowableValues = "15", required = true)
	private String purposeOfUsageId;
	
//	@NotNull
	@ApiModelProperty(value = "số chỗ ngồi / tải trọng xe", allowableValues = "1,2,3,4,5", required = true)
	private String tndsSoCho;
	
	@NotNull
	@ApiModelProperty(value = "Bảo hiểm trách nhiệm dân sự bắt buộc", allowableValues = "TRUE,FALSE", required = true)
	private Boolean tndsbbCheck;
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm trách nhiệm dân sự bắt buộc bao gồm VAT", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double tndsbbPhi;
	
	@NotNull
	@ApiModelProperty(value = "Bảo hiểm trách nhiệm dân sự tự nguyện", allowableValues = "TRUE,FALSE", required = true)
	private Boolean tndstnCheck;
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm trách nhiệm dân sự tự nguyện bao gồm VAT", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double tndstnPhi;
	
	@NotNull
	@ApiModelProperty(value = "Số tiền tham gia bảo hiểm", allowableValues = "50000000,100000000,150000000", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double tndstnSoTien;
	
	@NotNull
	@ApiModelProperty(value = "Tổng tiền thanh toán bao gồm VAT", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double totalPremium;
	
	@NotNull
	@ApiModelProperty(value = "Bảo hiểm vật chất xe", allowableValues = "TRUE,FALSE", required = true)
	private Boolean vcxCheck;
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm người ngồi trên xe ", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double vcxPhi;
	
	@NotNull
	@ApiModelProperty(value = "Số tiền bảo hiểm Vật chất xe", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double vcxSoTien;

	private String agencyRole;
}
