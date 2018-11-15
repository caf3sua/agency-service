package com.baoviet.agency.dto.eclaim;

import java.io.Serializable;

import com.baoviet.agency.dto.AgreementDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EclaimDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("claim_id")
	private String claim_id;

	@JsonProperty("policy_number")
	private String policy_number;
	
	@JsonProperty("policy_version")
	private String policy_version;
	
	@JsonProperty("claim_folder_number")
	private String claim_folder_number;
	
	@JsonProperty("claim_offer_number")
	private String claim_offer_number;
	
	@JsonProperty("request_date")
	private String request_date;
	
	@JsonProperty("loss_date")
	private String loss_date;
	
	@JsonProperty("payment_method")
	private Integer payment_method;
	
	@JsonProperty("claim_amount")
	private Double claim_amount;
	
	@JsonProperty("indemnify_amount")
	private Double indemnify_amount;
	
	@JsonProperty("currency_code")
	private String currency_code;
	
	@JsonProperty("note")
	private String note;
	
	@JsonProperty("status")
	private Integer status;
	
	@JsonProperty("system_date")
	private String system_date;
	
	@JsonProperty("valid_from_date")
	private String valid_from_date;
	
	@JsonProperty("valid_to_date")
	private String valid_to_date;
	
	@JsonProperty("risk_cause_id")
	private String risk_cause_id;
	
	@JsonProperty("risk_cause_name")
	private String risk_cause_name;
	
	@JsonProperty("hospital_id")
	private String hospital_id;
	
	@JsonProperty("hospital_name")
	private String hospital_name;
	
	@JsonProperty("address_of_loss")
	private String address_of_loss;
	
	@JsonProperty("benefit_acount_name")
	private String benefit_acount_name;
	
	@JsonProperty("benefit_acount_number")
	private String benefit_acount_number;
	
	@JsonProperty("benefit_acount_bank")
	private String benefit_acount_bank;
	
	@JsonProperty("photo_folder_name")
	private String photo_folder_name;
	
	@JsonProperty("identified_number")
	private String identified_number;
	
	@JsonProperty("claim_person")
	private String claim_person;
	
	@JsonProperty("claim_person_mobile")
	private String claim_person_mobile;
	
	@JsonProperty("claim_person_relationship")
	private String claim_person_relationship;
	
	@JsonProperty("claim_person_address")
	private String claim_person_address;
	
	@JsonProperty("claim_person_email")
	private String claim_person_email;
	
	@JsonProperty("reason_loss")
	private String reason_loss;
	
	@JsonProperty("isget")
	private Integer isget;
	
	@JsonProperty("error_msg")
	private String error_msg;
	
	@JsonProperty("priority")
	private String priority;
	
	@JsonProperty("staff_name")
	private String staff_name;
	
	@JsonProperty("staff_address")
	private String staff_address;
	
	@JsonProperty("staff_email")
	private String staff_email;
	
	@JsonProperty("staff_mobile")
	private String staff_mobile;
	
	private Integer numberClaim;
	
	private AgreementDTO agreementDTO;
}