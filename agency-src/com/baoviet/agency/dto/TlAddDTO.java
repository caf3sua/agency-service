package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TL_ADD database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TlAddDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tlAddId;
	
	@ApiModelProperty(value = "Địa chỉ người được bảo hiểm")
	private String address;

	@ApiModelProperty(value = "Số điện thoại Mobile người được bảo hiểm")
	private String cellPhone;

	@ApiModelProperty(value = "Thành phố")
	private String city;

	private String diagnose;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull
	@ApiModelProperty(value = "Ngày sinh", allowableValues = "dd/MM/yyyy",required = true)
	private Date dob;

	@ApiModelProperty(value = "Email")
	private String emailAdress;

	@ApiModelProperty(value = "Số điện thoại nhà")
	private String homePhone;
	
	@NotNull
	@ApiModelProperty(value = "Số Hộ chiếu/CMND", required = true)
	private String idPasswport;

	@NotNull
	@ApiModelProperty(value = "Họ và tên người được bảo hiểm", required = true)
	private String insuredName;

	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm", required = true)
	private Double premium;

	private String relationship;

	private Double si;

	private String title;

	private String tlId;

	@Override
	public String toString() {
		return "TlAddDTO [tlAddId=" + tlAddId + ", address=" + address + ", dob=" + dob + ", idPasswport=" + idPasswport
				+ ", insuredName=" + insuredName + ", premium=" + premium + ", relationship=" + relationship + ", si="
				+ si + ", title=" + title + ", tlId=" + tlId + "]";
	}
	
	
}