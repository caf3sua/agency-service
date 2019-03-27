package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.dto.TviCareAddDTO;
import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductTviVM extends ProductBaseVM {

	@ApiModelProperty(value = "Mã đơn hàng/Order code")
	private String soGycbh;

	@NotEmpty
	@ApiModelProperty(value = "Địa điểm đến/Destination", required = true)
	private String destinationId;

	@NotEmpty
	@ApiModelProperty(value = "Gói bảo hiểm dành cho/Insurance package", allowableValues = "1,3", required = true)
	private String travelWithId;

	@NotEmpty
	@ApiModelProperty(value = "Ngày bắt đầu/Inception date", allowableValues = "dd/MM/yyyy", required = true)
	private String inceptionDate;

	@NotEmpty
	@ApiModelProperty(value = "Ngày kết thúc/Expired date", allowableValues = "dd/MM/yyyy", required = true)
	private String expiredDate;

	@NotEmpty
	@ApiModelProperty(value = "Chương trình bảo hiểm/Insurance plan", allowableValues = "1,2,3,4,5", required = true)
	private String planId;

	private String departureId;

//	@ApiModelProperty(value = "Mã nguời yêu cầu bảo hiểm/Claimant id")
//	private String propserId;
//
//	@ApiModelProperty(value = "Nguời yêu cầu bảo hiểm/Claimant name")
//	private String propserName;
//
//	@ApiModelProperty(value = "Hộ chiếu-Chứng minh thư/Claimant passport")
//	private String propserCmt;
//
//	@ApiModelProperty(value = "Địa chỉ/Claimant address")
//	private String propserAddress;
//
//	@ApiModelProperty(value = "Địa chỉ (tỉnh)/Claimant province")
//	private String propserProvince;
//
//	@ApiModelProperty(value = "Email")
//	private String propserEmail;
//	
//	@ApiModelProperty(value = "Số điện thoại nhà/Claimant home phone")
//	private String propserHomephone;
//	
//	@ApiModelProperty(value = "Mobile/Claimant mobile")
//	private String propserCellphone;
	
	@ApiModelProperty(value = "Loại tiền tệ/Currency")
	private String bankId;
	@ApiModelProperty(value = "Mã sản phẩm/Policy number")
	private String policyNumber;

	@ApiModelProperty(value = "Mã tình trạng thanh toán/Status policy", allowableValues = "90")
	private String statusPolicyId;

	private String dateOfPayment;
	
//	@ApiModelProperty(value = "Xưng hô/Title")
//	private String propserTitle;
//	
//	@ApiModelProperty(value = "Ngày sinh/Date of birth", allowableValues = "dd/MM/yyyy")
//	private String propserNgaysinh;

	private String paymentMethod;
	
	@NotNull
	@ApiModelProperty(value = "Tổng phí bảo hiểm/Total premium", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private double premium;

	private String teamId;

	@JsonSerialize(using = DoubleSerializer.class)
	private double feeReceive;
	
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm/Premium", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private double netPremium;
	
	@NotNull
	@ApiModelProperty(value = "Phẩn trăm phí giảm/Premium discount", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private double changePremium;

	// More properties
	@ApiModelProperty(value = "Danh sách người được bảo hiểm/List of the insured")
	private List<TviCareAddDTO> listTviAdd;

	private String userAgent;
}
