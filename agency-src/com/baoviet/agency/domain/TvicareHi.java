package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the TVICARE_HIS database table.
 * 
 */
@Entity
@Table(name="TVICARE_HIS")
@Getter
@Setter
public class TvicareHi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TVICARE_ID")
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TVICARE_HIS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String tvicareId;

	@Column(name="ACCOUNT_NAME")
	private String accountName;

	@Column(name="ACCOUNT_NUMBER")
	private String accountNumber;

	@Column(name="AGENT_ID")
	private String agentId;

	@Column(name="AGENT_NAME")
	private String agentName;

	@Column(name="BANK_CONFIRM")
	private String bankConfirm;

	@Column(name="BANK_ID")
	private String bankId;

	@Column(name="BANK_NAME")
	private String bankName;

	@Column(name="BANK_NOTE")
	private String bankNote;

	@Column(name="BAOVIET_CONFIRM")
	private String baovietConfirm;

	@Column(name="BAOVIET_DEPARTMENT_ID")
	private String baovietDepartmentId;

	@Column(name="BAOVIET_DEPARTMENT_NAME")
	private String baovietDepartmentName;

	@Column(name="BAOVIET_ID")
	private String baovietId;

	@Column(name="BAOVIET_NAME")
	private String baovietName;

	@Column(name="BAOVIET_NOTE")
	private String baovietNote;

	@Column(name="CHANGE_PREMIUM")
	private Double changePremium;

	@Column(name="COUPONS_CODE")
	private String couponsCode;

	@Column(name="COUPONS_VALUE")
	private Double couponsValue;

	@Column(name="DATE_OF_PAYMENT")
	private Date dateOfPayment;

	@Column(name="DEPARTURE_ID")
	private String departureId;

	@Column(name="DEPARTURE_NAME")
	private String departureName;

	@Column(name="DESTINATION_ID")
	private String destinationId;

	@Column(name="DESTINATION_NAME")
	private String destinationName;

	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column(name="FEE_RECEIVE")
	private Double feeReceive;

	@Column(name="INCEPTION_DATE")
	private Date inceptionDate;

	@Column(name="MESSAGE_ID")
	private Double messageId;

	@Column(name="NET_PREMIUM")
	private Double netPremium;

	@Column
	private String note;

	@Column(name="PARENT_ID")
	private String parentId;

	@Column(name="PAYMENT_METHOD")
	private String paymentMethod;

	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="PLAN_NAME")
	private String planName;

	@Column(name="POLICY_DELIVER")
	private Date policyDeliver;

	@Column(name="POLICY_NUMBER")
	private String policyNumber;

	@Column
	private Double premium;

	@Column(name="PROPSER_ADDRESS")
	private String propserAddress;

	@Column(name="PROPSER_CELLPHONE")
	private String propserCellphone;

	@Column(name="PROPSER_CMT")
	private String propserCmt;

	@Column(name="PROPSER_EMAIL")
	private String propserEmail;

	@Column(name="PROPSER_HOMEPHONE")
	private String propserHomephone;

	@Column(name="PROPSER_ID")
	private String propserId;

	@Column(name="PROPSER_NAME")
	private String propserName;

	@Column(name="PROPSER_NGAYSINH")
	private Date propserNgaysinh;

	@Column(name="PROPSER_PROVINCE")
	private String propserProvince;

	@Column(name="PROPSER_TITLE")
	private String propserTitle;

	@Column(name="RECEIVER_ADDRESS")
	private String receiverAddress;

	@Column(name="RECEIVER_EMAIL")
	private String receiverEmail;

	@Column(name="RECEIVER_MOIBLE")
	private String receiverMoible;

	@Column(name="RECEIVER_NAME")
	private String receiverName;

	@Column(name="REQ_DATE")
	private Date reqDate;

	@Column(name="RESPONSE_DATE")
	private Date responseDate;

	@Column(name="SEND_DATE")
	private Date sendDate;

	@Column(name="SMARTAPP_SYSDATE")
	private Date smartappSysdate;

	@Column(name="SO_GYCBH")
	private String soGycbh;

	@Column(name="STATUS_ID")
	private String statusId;

	@Column(name="STATUS_NAME")
	private String statusName;

	@Column(name="STATUS_POLICY_ID")
	private String statusPolicyId;

	@Column(name="STATUS_POLICY_NAME")
	private String statusPolicyName;

	@Column(name="TEAM_ID")
	private String teamId;

	@Column(name="TEAM_NAME")
	private String teamName;

	@Column(name="TRAVEL_WITH_ID")
	private String travelWithId;

	@Column(name="TRAVEL_WITH_NAME")
	private String travelWithName;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;
}