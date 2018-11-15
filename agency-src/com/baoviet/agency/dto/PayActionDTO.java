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
 * The persistent class for the TL_ADD database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class PayActionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer payActionId;
	
	private Integer fromInviteCodeId;
	
	private String fromContactId;
	
	private String fromContactName;
	
	private String fromEmail;
	
	private String fromInviteCode;
	
	private String fromAccountNum;
	
	private String fromPhone;
	
	private String fromSendmail;
	
	private Integer toInviteCodeId;
	
	private String toContactId;
	
	private String toContactName;
	
	private String toEmail;
	
	private String toInviteCode;
	
	private String toAccountNum;
	
	private String toPhone;
	
	private String toSendmail;
	
	private String mciAddId;
	
	private String transactionId;
	
	private Integer refundStatus;
	
	private Integer status;
	
	private String bankcode;
	
	private String paymentGateway;
	
	private Double netAmount;
	
	private Integer refundAmount;
	
	private Double amount;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date transactionDate;
	
	private Double discountAmount;
	
	private Integer gif1Amount;
	
	private Double gif1Percent;

	private String gif1Code;
	
	private String gif1Info;
	
	private Integer gif1Refund;
	
	private Integer gif2Amount;

	private Double gif2Percent;
	
	private String gif2Code;
	
	private String gif2Info;
	
	private Integer gif2Refund;

	private Integer gif3Amount;
	
	private Double gif3Percent;
	
	private String gif3Code;
	
	private String gif3Info;
	
	private Integer gif3Refund;

	private Integer gif4Amount;
	
	private Double gif4Percent;
	
	private String gif4Code;
	
	private String gif4Info;
	
	private Integer gif4Refund;

	private Integer gif5Amount;
	
	private Double gif5Percent;
	
	private String gif5Code;
	
	private String gif5Info;
	
	private Integer gif5Refund;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date payStartDate;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date payEndDate;

	private String policyNumbers;
	
	private String payLog;

	private Integer statusEmailFrom;
	
	private Integer statusEmailTo;
	
	private Integer statusPayFrom;
	
	private Integer paymentDiscount;
	
	private Integer paymentRefund;
	
	private Integer statusPaymentRefund;

	private String numCard;
	
	private String bankName;
	
	private String cardName;
	
	private String address;
	
	private Integer statusCard;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date cardUpdateDate;
	
	private Integer statusEmailPayFrom;

}