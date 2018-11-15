package com.baoviet.agency.web.rest.vm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the CONTACT database table.
 * 
 */
@Getter
@Setter
public class ContactCreateVM implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@ApiModelProperty(value = "Tên khách hàng/Contact name", required = true)
	private String contactName;

	@ApiModelProperty(value = "Giới tính/Sex 1: Male, 2: Female", allowableValues = "1,2")
	private String contactSex;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày sinh/Date of birth", allowableValues = "dd/MM/yyyy", required = true)
	private Date dateOfBirth;

	@NotEmpty
	@ApiModelProperty(value = "Địa chỉ/Home address", required = true)
	private String homeAddress;

	@NotEmpty
	@ApiModelProperty(value = "Số điện thoại/Phone", required = true)
	private String phone;

	@NotEmpty
	@ApiModelProperty(value = "Email", required = true)
	private String email;

	@NotEmpty
	@ApiModelProperty(value = "Số hộ chiếu-CMND/Passport", required = true)
	private String idNumber;
	
	@ApiModelProperty(value = "Nghề nghiệp")
	private String occupation;
	
	@NotEmpty
	@ApiModelProperty(value = "Loại khách hàng", allowableValues = "POTENTIAL, FAMILIAR, VIP", required = true)
	private String groupType;
	
	@ApiModelProperty(value = "Danh sách mối quan hệ")
	private List<ContactRelationshipVM> listRelationship;
	
	@ApiModelProperty(value = "Danh sách mục sản phẩm khai thác")
	private List<ContactProductVM> listContactProduct;
	
	@ApiModelProperty(value = "Danh sách nhắc nhở")
	private List<ReminderCreateVM> listReminders;
	
	@ApiModelProperty(value = "Link Facebook")
	private String facebookId;
}