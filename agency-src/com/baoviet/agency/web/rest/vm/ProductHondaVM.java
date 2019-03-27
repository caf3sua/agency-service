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
public class ProductHondaVM extends ProductBaseVM {


	@NotEmpty
	@ApiModelProperty(value = "Họ và tên chủ xe", required = true)
	private String insuredName;

	@NotEmpty
	@ApiModelProperty(value = "Địa chỉ theo đăng ký xe", required = true)
	private String insuredAddress;

	@ApiModelProperty(value = "Số điện thoại chủ xe")
	private String insuredPhone;
	
	@ApiModelProperty(value = "Email thoại chủ xe")
	private String insuredEmail;

	@NotEmpty
	@ApiModelProperty(value = "Biển kiểm soát", required = true)
	private String registrationNumber; // get; set; }

	@NotEmpty
	@ApiModelProperty(value = "Số Khung", required = true)
	private String sokhung; // get; set; }

	@NotEmpty
	@ApiModelProperty(value = "Số Máy", required = true)
	private String somay; // get; set; }

	@ApiModelProperty(value = "Nhãn hiệu xe")
	private String hieuxe; // get; set; } // nhan hieu xe map voi ghichu

	@NotEmpty
	@ApiModelProperty(value = "Ngày bắt đầu hiệu lực BH", allowableValues = "dd/MM/yyyy", required = true)
	private String thoihantu; // get; set; } //map voi inception_date

	@ApiModelProperty(value = "Ngày kết thúc hiệu lực BH", allowableValues = "dd/MM/yyyy")
	private String thoihanden; // get; set; } // map voi expried_date

	@ApiModelProperty(value = "Nhóm loại xe", allowableValues = "1, 2")
	private String typeOfMoto; // ": "PGM1",

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

	// add 26/03/2019
	@ApiModelProperty(value = "Gói bảo hiểm", allowableValues = "1, 2, 3")
	private String goi;

	private String namSd;

	@NotNull
	@ApiModelProperty(value = "Id model xe", required = true)
	private String idModel;
	private String soTienBh;

}
