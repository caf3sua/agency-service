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
 * The persistent class for the TRAVELCARE_HIS database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TravelcareHiDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String travelcareId;

	private String accountName;

	private String accountNumber;

	private String agentId;

	private String agentName;

	private String bankConfirm;

	private String bankId;

	private String bankName;

	private String bankNote;

	private String baovietConfirm;

	private String baovietDepartmentId;

	private String baovietDepartmentName;

	private String baovietId;

	private String baovietName;

	private String baovietNote;

	private Double changePremium;

	private String couponsCode;

	private Double couponsValue;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfPayment;

	private String destinationId;

	private String destinationName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date expiredDate;

	private Double feeReceive;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date inceptionDate;

	private Double messageId;

	private Double netPremium;

	private String note;

	private String parentId;

	private String paymentMethod;

	private String planId;

	private String planName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date policyDeliver;

	private String policyNumber;

	private Double premium;

	private String propserAddress;

	private String propserCellphone;

	private String propserCmt;

	private String propserEmail;

	private String propserHomephone;

	private String propserId;

	private String propserName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date propserNgaysinh;

	private String propserProvince;

	private String propserTitle;

	private String receiverAddress;

	private String receiverEmail;

	private String receiverMoible;

	private String receiverName;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date reqDate;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date responseDate;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date sendDate;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date smartappSysdate;

	private String soGycbh;

	private String statusId;

	private String statusName;

	private String statusPolicyId;

	private String statusPolicyName;

	private String teamId;

	private String teamName;

	private String travelWithId;

	private String travelWithName;

	private String userId;

	private String userName;

}