package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TVI_CARE_ADD database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TviCareAddDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tviAddId;

	@ApiModelProperty(value = "Địa chỉ/Address")
	private String address;

	@ApiModelProperty(value = "Số điện thoại/CellPhone")
	private String cellPhone;

	@ApiModelProperty(value = "Thành Phố/City")
	private String city;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dayTreatment;

	private String detailTreatment;

	private String diagnose;

	@NotNull
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Ngày sinh người được bảo hiểm/Date of birth", allowableValues = "dd/MM/yyyy", required = true)
	private Date dob;

	@ApiModelProperty(value = "Email")
	private String emailAdress;

	@ApiModelProperty(value = "Số điện thoại nhà/Home phone")
	private String homePhone;

	@NotNull
	@ApiModelProperty(value = "Hộ chiếu người được BH/Passwport", required = true)
	private String idPasswport;

	@NotNull
	@ApiModelProperty(value = "Tên người được bảo hiểm/Insured name", required = true)
	private String insuredName;

	private String nameDoctor;

	private String relationship;

	private String resultTreatment;

	@ApiModelProperty(value = "Xưng hô/Title")
	private String title;

	private String travaelcareId;
	
	@NotEmpty
	@ApiModelProperty(value = "Mã Quan hệ người được bảo hiểm với người YCBH/Relationship id", allowableValues = "30, 31, 32, 33, 34, 39", required = true)
	private String relationshipId;
	
	@ApiModelProperty(value = "Tên quan hệ/Relationship name")
	private String relationshipName;
	
	private String tviCareId;
}