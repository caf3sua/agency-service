package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.bean.InvoiceInfoDTO;
import com.baoviet.agency.bean.ReceiverUserInfoDTO;
import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMotoMOMOVM {

	@NotNull
	@ApiModelProperty(value = "Thông tin người nhận đơn bảo hiểm", required = true)
   	private ReceiverUserInfoDTO receiverUser;
    
	@ApiModelProperty(value = "Thông tin hóa đơn GTGT")
   	private InvoiceInfoDTO invoiceInfo;
	
	@NotEmpty
	@ApiModelProperty(value = "Tên khách hàng", required = true)
	private String contactName;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày sinh khách hàng ", allowableValues = "dd/MM/yyyy", required = true)
	private String contactDob;
	
	@NotEmpty
	@ApiModelProperty(value = "Số điện thoại ", required = true)
	private String contactPhone;
	
	@NotEmpty
	@ApiModelProperty(value = "Email ", required = true)
	private String contactEmail;
	
	@NotEmpty
	@ApiModelProperty(value = "CMT/MST ", required = true)
	private String contactIdNumber;
	
	@NotEmpty
	@ApiModelProperty(value = "Địa chỉ khách hàng", required = true)
	private String contactAddress;
	
	@ApiModelProperty(value = "Phương thức nhận đơn bảo hiểm(1: nhận bản mềm, 2: nhận bản cứng)", allowableValues = "1,2")
    private String receiveMethod;
	
	@ApiModelProperty(value = "Số GYCBH cũ trong trường hợp tái tục")
    private String oldGycbhNumber;
	
    @NotEmpty
    @ApiModelProperty(value = "Họ và tên chủ xe", required = true)
	private String insuredName  ; 
	
    @NotEmpty
    @ApiModelProperty(value = "Địa chỉ theo đăng ký xe", required = true)
	private String insuredAddress  ; 
	
    @NotEmpty
    @ApiModelProperty(value = "Biển kiểm soát", required = true)
	private String registrationNumber; 
	
    @NotEmpty
    @ApiModelProperty(value = "Số Khung", required = true)
	private String sokhung; 
	
    @NotEmpty
    @ApiModelProperty(value = "Số Máy", required = true)
	private String somay; 
	
    @ApiModelProperty(value = "Nhãn hiệu xe")
	private String hieuxe;  // nhan hieu xe map voi ghichu
	
    @NotEmpty
    @ApiModelProperty(value = "Ngày bắt đầu hiệu lực BH", allowableValues = "dd/MM/yyyy", required = true)
	private String thoihantu;  //map voi inception_date
	
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
