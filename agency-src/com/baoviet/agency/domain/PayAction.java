package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TL_ADD database table.
 * 
 */
@Entity
@Table(name="PAY_ACTION")
@Getter
@Setter
public class PayAction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PAY_ACTION_ID", unique=true)
	@SequenceGenerator(name= "NAME_SEQUENCE", sequenceName = "PAY_ACTION_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NAME_SEQUENCE")
	private Long payActionId;
	
	@Column
	private Integer fromInviteCodeId;
	
	@Column
	private String fromContactId;
	
	@Column
	private String fromContactName;
	
	@Column
	private String fromEmail;
	
	@Column
	private String fromInviteCode;
	
	@Column
	private String fromAccountNum;
	
	@Column
	private String fromPhone;
	
	@Column
	private String fromSendmail;
	
	@Column
	private Integer toInviteCodeId;
	
	@Column
	private String toContactId;
	
	@Column
	private String toContactName;
	
	@Column
	private String toEmail;
	
	@Column
	private String toInviteCode;
	
	@Column
	private String toAccountNum;
	
	@Column
	private String toPhone;
	
	@Column
	private String toSendmail;
	
	@Column
	private String mciAddId;
	
	@Column
	private String transactionId;
	
	@Column
	private Integer refundStatus;
	
	@Column
	private Integer status;
	
	@Column
	private String bankcode;
	
	@Column
	private String paymentGateway;
	
	@Column
	private Double netAmount;
	
	@Column
	private Integer refundAmount;
	
	@Column
	private Double amount;
	
	@Temporal(TemporalType.DATE)
	@Column
	private Date transactionDate;
	
	@Column
	private Double discountAmount;
	
	@Column(name="GIF1_AMOUNT")
	private Integer gif1Amount;
	
	@Column(name="GIF1_PERCENT")
	private Double gif1Percent;

	@Column(name="GIF1_CODE")
	private String gif1Code;
	
	@Column(name="GIF1_INFO")
	private String gif1Info;
	
	@Column(name="GIF1_REFUND")
	private Integer gif1Refund;
	
	@Column(name="GIF2_AMOUNT")
	private Integer gif2Amount;

	@Column(name="GIF2_PERCENT")
	private Double gif2Percent;
	
	@Column(name="GIF2_CODE")
	private String gif2Code;
	
	@Column(name="GIF2_INFO")
	private String gif2Info;
	
	@Column(name="GIF2_REFUND")
	private Integer gif2Refund;

	@Column(name="GIF3_AMOUNT")
	private Integer gif3Amount;
	
	@Column(name="GIF3_PERCENT")
	private Double gif3Percent;
	
	@Column(name="GIF3_CODE")
	private String gif3Code;
	
	@Column(name="GIF3_INFO")
	private String gif3Info;
	
	@Column(name="GIF3_REFUND")
	private Integer gif3Refund;

	@Column(name="GIF4_AMOUNT")
	private Integer gif4Amount;
	
	@Column(name="GIF4_PERCENT")
	private Double gif4Percent;
	
	@Column(name="GIF4_CODE")
	private String gif4Code;
	
	@Column(name="GIF4_INFO")
	private String gif4Info;
	
	@Column(name="GIF4_REFUND")
	private Integer gif4Refund;

	@Column(name="GIF5_AMOUNT")
	private Integer gif5Amount;
	
	@Column(name="GIF5_PERCENT")
	private Double gif5Percent;
	
	@Column(name="GIF5_CODE")
	private String gif5Code;
	
	@Column(name="GIF5_INFO")
	private String gif5Info;
	
	@Column(name="GIF5_REFUND")
	private Integer gif5Refund;

	@Temporal(TemporalType.DATE)
	@Column
	private Date payStartDate;
	
	@Temporal(TemporalType.DATE)
	@Column
	private Date payEndDate;

	@Column
	private String policyNumbers;
	
	@Column
	private String payLog;

	@Column
	private Integer statusEmailFrom;
	
	@Column
	private Integer statusEmailTo;
	
	@Column
	private Integer statusPayFrom;
	
	@Column
	private Integer paymentDiscount;
	
	@Column
	private Integer paymentRefund;
	
	@Column
	private Integer statusPaymentRefund;

	@Column
	private String numCard;
	
	@Column
	private String bankName;
	
	@Column
	private String cardName;
	
	@Column
	private String address;
	
	@Column
	private Integer statusCard;
	
	@Temporal(TemporalType.DATE)
	@Column
	private Date cardUpdateDate;
	
	@Column
	private Integer statusEmailPayFrom;

}