package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MOTO database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class MotoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date bvSysdate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private Double chaynoPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double chaynoStbh = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String companyId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String compnayName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String contactUsername = AgencyConstants.DEFAULT_STRING_VALUE;

	private String couponsCode = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double couponsValue = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String customerAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerIdNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String customerPhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String departmentId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String departmentName = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String ghichu = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private String insuredAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String insuredEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String insuredName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String insuredPhone = AgencyConstants.DEFAULT_STRING_VALUE;

	private String make = AgencyConstants.DEFAULT_STRING_VALUE;

	private String model = AgencyConstants.DEFAULT_STRING_VALUE;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayNopPhi = AgencyConstants.DEFAULT_DATE_NOW_VALUE;

	private Integer nntxSoNguoi = AgencyConstants.DEFAULT_INTEGER_VALUE;

	private Double nntxStbh = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String policyNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String policyStatus = AgencyConstants.DEFAULT_STRING_VALUE;

	private String policyStatusName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverAddress = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverEmail = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverMoible = AgencyConstants.DEFAULT_STRING_VALUE;

	private String receiverName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String registrationNumber = AgencyConstants.DEFAULT_STRING_VALUE;

	private String soGycbh = AgencyConstants.DEFAULT_STRING_VALUE;

	private String soGycbhId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String sokhung = AgencyConstants.DEFAULT_STRING_VALUE;

	private String somay = AgencyConstants.DEFAULT_STRING_VALUE;

	private String status = AgencyConstants.DEFAULT_STRING_VALUE;

	private String statusName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double tndsBbPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double tndsTnNguoi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double tndsTnNntxPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double tndsTnPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double tndsTnTs = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double tongPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private String typeOfMotoId = AgencyConstants.DEFAULT_STRING_VALUE;

	private String typeOfMotoName = AgencyConstants.DEFAULT_STRING_VALUE;

	private String userNhap = AgencyConstants.DEFAULT_STRING_VALUE;

	private String userNhapName = AgencyConstants.DEFAULT_STRING_VALUE;

	private Double vcxPhi = AgencyConstants.DEFAULT_DOUBLE_VALUE;

	private Double vcxStbh = AgencyConstants.DEFAULT_DOUBLE_VALUE;

}