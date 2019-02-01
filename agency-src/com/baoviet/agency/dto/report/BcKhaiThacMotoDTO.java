package com.baoviet.agency.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BcKhaiThacMotoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String statusPolicyId;
	private String dateOfPayment;
	private String insuredName; 
	private String insuredAddressDiaChi;
	private String insuredAddressPhuongXa;
	private String insuredPhone;
	private String receiverName; 
	private String receiverTinhTp;
	private String receiverQuanHuyen;
	private String receiverPhuongXa;
	private String receiverDiaChi;
	private String receiverMoible;
	private String registrationNumber;
	private String sokhung;
	private String somay;
	private String policyNumber;
	private String typeOfMotoName;
	private String inceptionDate;
	private String expiredDate;
	private BigDecimal tndsBbPhi;
	private BigDecimal tndsTnNguoi;
	private BigDecimal tndsTnTs;
	private BigDecimal tndsTnPhi;
	private BigDecimal nntxSoNguoi;
	private BigDecimal nntxStbh;
	private BigDecimal nntxPhi;
	private BigDecimal chaynoStbh;
	private BigDecimal chaynoPhi;
	private BigDecimal vcxStbh;
	private BigDecimal vcxPhi;
	private BigDecimal tongPhi;
	private String mciAddId;
	private String agreementId;
	private String paymentGateway;
	private String ghichu;
}