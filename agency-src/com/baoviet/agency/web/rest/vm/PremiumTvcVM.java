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
public class PremiumTvcVM {

	@NotEmpty
	@ApiModelProperty(value = "Gói bảo hiểm dành cho/Insurance package", allowableValues = "1,2,3", required = true)
	private String premiumPackage;
	
	@NotNull
	@ApiModelProperty(value = "Số người tham gia/Number of person", required = true)
	private int numberOfPerson;
	
	@NotNull
	@ApiModelProperty(value = "Ngày đi/Inception Date", allowableValues = "dd/MM/yyyy", required = true)
	private String ngayDi;
	
	@NotEmpty
	@ApiModelProperty(value = "Ngày về/Expired Date", allowableValues = "dd/MM/yyyy", required = true)
	private String ngayVe;
	
	@NotEmpty
	@ApiModelProperty(value = "Chương trình bảo hiểm/Insurance plan", allowableValues = "1,2,3,4", required = true)
	private String planId;
	
	@NotEmpty
	@ApiModelProperty(value = "Nơi đến/Destination", allowableValues = "2,3,4", required = true)
	private String destination;
	
	@NotNull
	@ApiModelProperty(value = "Tổng phí bảo hiểm/Total premium", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double premiumTvc;
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm/Premium", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double premiumNet;
	
	@NotNull
	@ApiModelProperty(value = "Phẩn trăm phí giảm/Premium discount", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
	private Double premiumDiscount;

	// More attribute
	@ApiModelProperty(value = "Số ngày/Number of day")
	private int songay;

	@Override
	public String toString() {
		return "PremiumTvcVM [premiumPackage=" + premiumPackage + ", numberOfPerson=" + numberOfPerson + ", ngayDi="
				+ ngayDi + ", ngayVe=" + ngayVe + ", planId=" + planId + ", destination=" + destination
				+ ", premiumTvc=" + premiumTvc + ", premiumNet=" + premiumNet + ", premiumDiscount=" + premiumDiscount
				+ ", songay=" + songay + "]";
	}

}