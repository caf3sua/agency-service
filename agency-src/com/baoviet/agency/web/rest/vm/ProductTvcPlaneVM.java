package com.baoviet.agency.web.rest.vm;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DateSerializer;
import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * TvcPlaneBaseVM
 * 
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class ProductTvcPlaneVM extends ProductBaseVM{

	@ApiModelProperty(value = "Số Hợp đồng/Giấy chứng nhận BH/Policy number")
	private String policyNumber;

	@NotEmpty
	@ApiModelProperty(value = "Chặng bay 1: Nội địa, 2: Quốc tế/Flight 1: Domestic, 2: International", allowableValues = "1,2", required = true)
	private String areaId;

	@NotEmpty
	@ApiModelProperty(value = "Kiểu bay 1: Một chiều, 2: Khứ hồi/Flight type 1: One way, 2: Return", allowableValues = "1,2", required = true)
	private String planId;

	@NotEmpty
	@ApiModelProperty(value = "Nơi khởi hành/Place from", required = true)
	private String placeFrom;

	@NotEmpty
	@ApiModelProperty(value = "Nơi đến/Place to", required = true)
	private String placeTo;

	@NotNull
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày khởi hành/Inception date", allowableValues = "dd/MM/yyyy", required = true)
	private Date inceptionDate;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày về/Expired date", allowableValues = "dd/MM/yyyy")
	private Date expiredDate;

	@NotEmpty
	@ApiModelProperty(value = "Tên khách hàng/Customer name", required = true)
	private String customerName;

	@NotEmpty
	@ApiModelProperty(value = "Id khách hàng/Customer id", required = true)
	private String customerId;

	@NotEmpty
	@ApiModelProperty(value = "Mã khách hàng/Customer code", required = true)
	private String customerCode;

	@ApiModelProperty(value = "Số hộ chiếu,CMND/Passport")
	private String customerCmt;

	@ApiModelProperty(value = "Địa khách hàng/Customer address")
	private String customerAddress;

	@NotEmpty
	@ApiModelProperty(value = "Số điện thoại khách hàng/Customer phone", required = true)
	private String customerPhone;

	@NotEmpty
	@Email
	@ApiModelProperty(value = "Email khách hàng/Customer email", required = true)
	private String customerEmail;

	@NotEmpty
	@ApiModelProperty(value = "Giới tính khách hàng 1: Nam, 2: Nữ/Sex 1: Male, 2: Female", allowableValues = "1,2", required = true)
	private String sexId;

	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm/Premium", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double netPremium;

	@ApiModelProperty(value = "Tăng-Giảm phí/Premium discount", allowableValues = "0")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double changePremium;

	@NotNull
	@ApiModelProperty(value = "Tổng tiền thanh toán/Total premium", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double totalPremium;

	@NotNull
	@ApiModelProperty(value = "Loại đối tác Bảo Việt/Type Of Agency", allowableValues = "GOTADI", required = true)
	private String agencyType;

	@ApiModelProperty(value = "Danh sách người được bảo hiểm/List of the insured")
	private List<TvcPlaneAddBaseVM> lstTvcPlane;
	// More attribute
    String userAgent;

}
