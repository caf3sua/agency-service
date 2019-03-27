package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * HomeBase
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class ProductHomeVM extends ProductBaseVM {

	// step 01
	@NotEmpty
	@ApiModelProperty(value = "Thời hạn bảo hiểm", allowableValues = "1,2", required = true)
    private String yearBuildCode;

	@NotEmpty
	@ApiModelProperty(value = "Giới hạn bồi thường", required = true)
    private String si; // gioi han boi thuong
    
	@ApiModelProperty(value = "Phí giới hạn bồi thường")
	@JsonSerialize(using = DoubleSerializer.class)
	private Double siPremium;

	@ApiModelProperty(value = "Giới hạn bồi thường tài sản bên trong", allowableValues = "100000000,300000000,500000000,750000000,1000000000")
    private String siin;

	@ApiModelProperty(value = "Phí giới hạn bồi thường tài sản bên trong")
	@JsonSerialize(using = DoubleSerializer.class)
    private Double siinPremium; 

	@ApiModelProperty(value = "Phí bảo hiểm phần ngôi nhà")
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumsi;

	@ApiModelProperty(value = "Phí bảo hiểm phần ngôi nhà bên trong")
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumsiin;

	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumHome;
	
	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumNet;
	
	@NotNull
	@ApiModelProperty(value = "Phẩn trăm phí giảm ", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private double premiumDiscount;

    // step 02: thong tin ngoi nha
	@NotEmpty
	@ApiModelProperty(value = "Họ và tên người được bảo hiểm", required = true)
	//@Size(min = 1, max = 100)
    private String insuranceName;
	
	@NotEmpty
	@ApiModelProperty(value = "Số chứng minh nhân dân/hộ chiếu: người được bảo hiểm", required = true)
    private String invoceNumber; 
	
	@NotEmpty
	@ApiModelProperty(value = "Địa chỉ: người được bảo hiểm", required = true)
    private String insuranceAddress; 
	
	@NotEmpty
	@ApiModelProperty(value = "Điện thoại: người được bảo hiểm", required = true)
    private String byNight;
	
	@NotEmpty
	@ApiModelProperty(value = "Địa chỉ ngôi nhà mua bảo hiểm", required = true)
    private String insuredLocation;

	@NotEmpty
	@ApiModelProperty(value = "Chủ sở hữu", allowableValues = "0,1,2", required = true)
    private String bankId;

	@NotEmpty
	@ApiModelProperty(value = "Tổng diện tích sử dụng", required = true)
    private String totalUsedArea;
	
	@NotEmpty
	@ApiModelProperty(value = "Loại hình ngôi nhà", allowableValues = "0,1", required = true)
    private String loaiHinh; // loại hình ngôi nhà:(*) nhận 0 là chung cư, 1 là liền kề
    
	@NotEmpty
	@ApiModelProperty(value = "Thời hạn bảo hiểm từ", allowableValues = "dd/MM/yyyy", required = true)
    private String inceptionDate;
	
	@ApiModelProperty(value = "Thời hạn bảo hiểm đến", allowableValues = "dd/MM/yyyy")
    private String expiredDate;
	
	@NotEmpty
	@ApiModelProperty(value = "Câu hỏi: Ngôi nhà được bảo hiểm xây dựng với mục đích để ở và phục vụ sinh hoạt gia đình?", allowableValues = "0,1", required = true)
    private String windowLocks; // để ở và phục vụ sinh hoạt gia đình? nhận giá trị 0 nếu trả lời là có và 1 nếu là trả lời 0
    
	@NotEmpty
	@ApiModelProperty(value = "Câu hỏi: Ngôi nhà được bảo hiểm được xây dưng bằng gạch, đá, xi măng?", allowableValues = "0,1", required = true)
	private String bars; // ngôi nhà được bảo hiểm được xây dưng bằng gạch, đá, xi măng? nhận giá trị 0 nếu có và 1 nếu không

    private String baovietCompanyId;
    private String baovietCompanyName;
    
    @ApiModelProperty(value = "Số HĐ/GCN")
	public String policyNumber;
    
    // More attribute
    String userAgent;

}
