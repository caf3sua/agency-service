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
public class ProductCarVM extends ProductBaseVM {

	@ApiModelProperty(value = "Giá trị xe theo thị trường")
	@JsonSerialize(using = DoubleSerializer.class)
	private double actualValue;

	@ApiModelProperty(value = "Số tiền giảm phí")
	@JsonSerialize(using = DoubleSerializer.class)
	private double changePremium;

	@NotEmpty
	@ApiModelProperty(value = "Số Khung", required = true)
	private String chassisNumber;

	@NotEmpty
	@ApiModelProperty(value = "Số Máy", required = true)
	private String engineNumber;

	@ApiModelProperty(value = "Tổng phí bảo hiểm bao gồm VAT", allowableValues = "TRUE,FALSE")
	private Boolean garageCheck;

	@NotEmpty
	@ApiModelProperty(value = "Địa chỉ theo đăng ký xe", required = true)
	private String insuredAddress;

	@NotEmpty
	@ApiModelProperty(value = "Họ và tên chủ xe", required = true)
	private String insuredName;

	@ApiModelProperty(value = "Điều khoản không khấu hao thay mới", allowableValues = "TRUE,FALSE")
	private Boolean khaoHaoCheck;
	
	@ApiModelProperty(value = "Tham gia điều khoản không khấu trừ 500000/vụ", allowableValues = "TRUE,FALSE")
	private Boolean khauTruCheck;
	
	private String makeId;
	
	@ApiModelProperty(value = "Tên Hãng xe")
	private String makeName;
	
	@ApiModelProperty(value = "Điều khoản mất cắp bộ phận", allowableValues = "TRUE,FALSE")
	private Boolean matCapCheck;
	
	@ApiModelProperty(value = "Mã Model xe")
	private String modelId;
	
	@ApiModelProperty(value = "Tên Model xe")
	private String modelName;

	@NotNull
	@ApiModelProperty(value = "Bảo hiểm người ngồi trên xe", allowableValues = "TRUE,FALSE", required = true)
	private Boolean nntxCheck;

	@NotNull
	@ApiModelProperty(value = "Điều khoản ngập nước", allowableValues = "TRUE,FALSE", required = true)
	private Boolean ngapNuocCheck;

	@ApiModelProperty(value = "Số người ngồi trên xe")
	@JsonSerialize(using = DoubleSerializer.class)
	private double passengersAccidentNumber;

	@ApiModelProperty(value = "Phí bảo hiểm người ngồi trên xe")
	@JsonSerialize(using = DoubleSerializer.class)
	private double passengersAccidentPremium;

	@NotNull
	@ApiModelProperty(value = "Số tiền bảo hiểm NNTX", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private double passengersAccidentSi;

	@ApiModelProperty(value = "Số HĐ/GCN")
	private String policyNumber;

	@NotNull
	@ApiModelProperty(value = "Tổng phí bảo hiểm bao gồm VAT", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private double premium;

	@NotNull
	@ApiModelProperty(value = "nhóm loại xe", allowableValues = "15", required = true)
	private String purposeOfUsageId;

	@ApiModelProperty(value = "Phí bảo hiểm VCX bao gồm VAT")
	@JsonSerialize(using = DoubleSerializer.class)
	private double physicalDamagePremium;
	
	@ApiModelProperty(value = "Số tiền tham gia bảo hiểm VCX")
	@JsonSerialize(using = DoubleSerializer.class)
	private double physicalDamageSi;

	@NotEmpty
	@ApiModelProperty(value = "Biển kiểm soát", required = true)
	private String registrationNumber;

	@ApiModelProperty(value = "số chỗ ngồi / tải trọng xe", allowableValues = "1,2,3,4,5")
	private String tndsSoCho;

	@NotNull
	@ApiModelProperty(value = "Bảo hiểm trách nhiệm dân sự bắt buộc", allowableValues = "TRUE,FALSE", required = true)
	private Boolean tndsbbCheck;

	@NotNull
	@ApiModelProperty(value = "Bảo hiểm trách nhiệm dân sự tự nguyện", allowableValues = "TRUE,FALSE", required = true)
	private Boolean tndstnCheck;

	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm trách nhiệm dân sự tự nguyện bao gồm VAT", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private double tndstnPhi;

	@NotNull
	@ApiModelProperty(value = "Số tiền tham gia bảo hiểm", allowableValues = "50000000,100000000,150000000", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private double tndstnSotien;

	@NotNull
	@ApiModelProperty(value = "Tổng tiền thanh toán bao gồm VAT", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private double totalPremium;

	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm trách nhiệm dân sự bắt buộc bao gồm VAT", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private double thirdPartyPremium;

	@ApiModelProperty(value = "Ngày kết thúc hiệu lực BH", allowableValues = "dd/MM/yyyy")
	private String thoihanden;

	@NotNull
	@ApiModelProperty(value = "Ngày bắt đầu hiệu lực BH", allowableValues = "dd/MM/yyyy", required = true)
	private String thoihantu;

	@NotNull
	@ApiModelProperty(value = "Bảo hiểm vật chất xe", allowableValues = "TRUE,FALSE", required = true)
	private Boolean vcxCheck;

	@ApiModelProperty(value = "Năm sản xuất xe")
	private String yearOfMake;

	@ApiModelProperty(value = "Tháng sản xuất xe")
	private String monthOfMake;
	
	@ApiModelProperty(value = "Loại khách hàng", allowableValues = "PERSON,ORGANIZATION") 
	private String categoryType;
	
	@ApiModelProperty(value = "Gói bảo hiểm 1: cơ bản, 2: linh hoạt", allowableValues = "1,2") 
	private String packageType;
	
	// More attribute
    String userAgent;
}

