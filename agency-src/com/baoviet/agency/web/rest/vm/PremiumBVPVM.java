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
public class PremiumBVPVM {

	@NotEmpty
	@ApiModelProperty(value = "Chương trình bảo hiểm", allowableValues = "1,2,3,4,5", required = true)
	private String chuongTrinh;

	@NotEmpty
	@ApiModelProperty(value = "Ngày sinh người được bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
	private String ngaySinh;

	@NotNull
	@ApiModelProperty(value = "Tuổi người được bảo hiểm", required = true)
	private Integer tuoi;

	@NotNull
	@ApiModelProperty(value = "Tham gia quyền lợi Ngoại trú", allowableValues = "TRUE,FALSE", required = true)
	private Boolean ngoaitruChk;
	
	@ApiModelProperty(value = "Phí quyền lợi ngoại trú")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double ngoaitruPhi;

	@NotNull
	@ApiModelProperty(value = "Tham gia quyền lợi Tai nạn cá nhân", allowableValues = "TRUE,FALSE", required = true)
	private Boolean tncnChk;
	
	@ApiModelProperty(value = "Số tiền bảo hiểm TNCN")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double tncnSi;
	
	@ApiModelProperty(value = "Phí bảo hiểm quyền lợi TNCN")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double tncnPhi;

	@NotNull
	@ApiModelProperty(value = "Tham gia quyền lợi Sinh mạng cá nhân", allowableValues = "TRUE,FALSE", required = true)
	private Boolean smcnChk;
	
	@ApiModelProperty(value = "Số tiền bảo hiểm SMCN")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double smcnSi;
	
	@ApiModelProperty(value = "Phí bảo hiểm quyền lợi SMCN")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double smcnPhi;

	@NotNull
	@ApiModelProperty(value = "Tham gia quyền lợi Nha Khoa", allowableValues = "TRUE,FALSE", required = true)
	private Boolean nhakhoaChk;
	
	@ApiModelProperty(value = "Phí bảo hiểm quyền lợi Nha khoa")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double nhakhoaPhi;
	
	@NotNull
	@ApiModelProperty(value = "Tham gia quyền lợi thai sản", allowableValues = "TRUE,FALSE", required = true)
	private Boolean thaisanChk;
	
	@ApiModelProperty(value = "Phí quyền lợi thai sản")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double thaisanPhi;

	@NotEmpty
	@ApiModelProperty(value = "Ngày bắt đầu hiệu lực bảo hiểm", allowableValues = "dd/MM/yyyy", required = true)
	private String thoihanbhTu;

	@NotEmpty
	@ApiModelProperty(value = "Mã đối tác Bảo Việt", allowableValues = "NCB", required = true)
	private String pAgencyRole;

	@ApiModelProperty(value = "Phí quyền lợi chính")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double qlChinhPhi;
	
	@ApiModelProperty(value = "Phí bảo hiểm")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double phiBH;

	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double premiumNet;
	
	@ApiModelProperty(value = "Phần trăm giảm phí")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double premiumDiscount;
}
