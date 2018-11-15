package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MOTO_HIS database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class MotoHiDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date bvSysdate;

	private Double chaynoPhi;

	private Double chaynoStbh;

	private String companyId;

	private String compnayName;

	private String contactCode;

	private String contactId;

	private String contactUsername;

	private String couponsCode;

	private Double couponsValue;

	private String customerAddress;

	private String customerEmail;

	private String customerIdNumber;

	private String customerName;

	private String customerPhone;

	private String departmentId;

	private String departmentName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate;

	private String ghichu;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate;

	private String insuredAddress;

	private String insuredEmail;

	private String insuredName;

	private String insuredPhone;

	private String make;

	private String model;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayNopPhi;

	private String parentId;

	private String policyNumber;

	private String policyStatus;

	private String policyStatusName;

	private String receiverAddress;

	private String receiverEmail;

	private String receiverMoible;

	private String receiverName;

	private String registrationNumber;

	private String soGycbh;

	private String soGycbhId;

	private String sokhung;

	private String somay;

	private String status;

	private String statusName;

	private Double tndsBbPhi;

	private Double tndsTnNguoi;

	private Double tndsTnNntxPhi;

	private Double tndsTnPhi;

	private Double tndsTnTs;

	private Double tongPhi;

	private String typeOfMotoId;

	private String typeOfMotoName;

	private String userNhap;

	private String userNhapName;

	private Double vcxPhi;

	private Double vcxStbh;

}