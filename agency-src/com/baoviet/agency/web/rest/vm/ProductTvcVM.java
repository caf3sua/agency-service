package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductTvcVM extends ProductBaseVM {

//	@NotEmpty
	@ApiModelProperty(value = "Số GYC-Hợp đồng BH/Policy number")
	private String policyNumber;

	@NotNull
	@ApiModelProperty(value = "Gói bảo hiểm dành cho/Insurance package",allowableValues = "1,2,3", required = true)
    private String travelWithId;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày khởi hành/Inception date", allowableValues = "dd/MM/yyyy", required = true)
    private String inceptionDate;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày trở về/Expired date", allowableValues = "dd/MM/yyyy", required = true)
    private String expiredDate;
    
	@NotEmpty
	@ApiModelProperty(value = "Chương trình bảo hiểm/Insurance plan", allowableValues = "1,2,3,4", required = true)
    private String planId;        
    
	@NotEmpty
    @ApiModelProperty(value = "Nơi đến/Destination", allowableValues = "2,3,4", required = true)
    private String destinationId;
    
	@NotEmpty
	@ApiModelProperty(value = "Họ tên người yêu cầu BH/Claimant name", required = true)
    private String propserName;
    
    @ApiModelProperty(value = "Số điện thoại người yêu cầu BH/Claimant phone")
    private String propserCellphone;
    
    @NotEmpty
	@ApiModelProperty(value = "Ngày sinh người yêu cầu BH/Claimant date of birth", allowableValues = "dd/MM/yyyy", required = true)
    private String propserNgaysinh;
    
    private String paymentMethod;
    
    @NotNull
	@ApiModelProperty(value = "Tổng phí bảo hiểm/Total premium", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double premium;
    
    @NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm/Premium", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double netPremium;
    
    @NotNull
	@ApiModelProperty(value = "Phẩn trăm phí giảm/Premium discount", required = true)
    @JsonSerialize(using = DoubleSerializer.class)
    private Double changePremium;
    
    //truong tao them de phuc vu viec tinh phi
    @NotNull
	@ApiModelProperty(value = "Loại tiền trong bảng quyền lợi/Currency", allowableValues = "USD, EUR", required = true)
    private String loaitien; //anh xa cua bankId

    @NotNull
	@ApiModelProperty(value = "Số người tham gia/Number of persons", required = true)
    private Integer soNguoiThamGia;

    @NotNull
    @ApiModelProperty(value = "Gói bảo hiểm dành cho/Insurance package", allowableValues = "1,2,3", required = true)
    private String tvcPackage; 
    
    // More properties
   
    @ApiModelProperty(value = "Danh sách người được bảo hiểm/List of the insured")
    private List<TvcAddBaseVM> listTvcAddBaseVM;
    
    private String agreementId;
    private Integer travelCareId;
}
