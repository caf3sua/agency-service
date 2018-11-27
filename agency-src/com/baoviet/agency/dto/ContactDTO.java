package com.baoviet.agency.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baoviet.agency.domain.ContactProduct;
import com.baoviet.agency.domain.ContactRelationship;
import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@JsonInclude(Include.NON_EMPTY)
public class ContactDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String contactId;

	private String companyId;

	private String companyName;

	private String companyWork;

	private String contactCode;

	private String contactComment;

	private String contactName;

	private String contactNameEn;

	private String contactNameSearch;

	private String contactPassword;

	private String contactSex;

	private String contactSexName;

	private String contactUsername;

	private BigDecimal countLogin;

	private String createdById;

	private String createdByName;

//	@JsonSerialize(as = Date.class)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfBirth;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfIssue;

	private String departmentId;

	private String departmentName;

	private String email;

	private String handPhone;

	private String homeAddress;

	private String homeAddressMail;

	private String idNumber;

	private String isDelete;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date lastDateLogin;

	private String occupation;

	private String phone;

	private String placeOfIssue;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date smartappSysdate;

	private String status;

	private String type;

	private String typeOfContact;

	private String typeOfContactName;

	private String userId;

	private String userName;
	
	private String groupType;
	
	private String categoryType;
	
	@ApiModelProperty(value = "Danh sách mối quan hệ")
	private List<ContactRelationship> listRelationship;
	
	@ApiModelProperty(value = "Danh sách mục sản phẩm khai thác")
	private List<ContactProduct> listContactProduct;
	
	private String facebookId;
}