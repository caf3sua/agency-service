package com.baoviet.agency.bean;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/** 
* 
* @author: Nam, Nguyen Hoai 
*/
@Getter
@Setter
public class AgreementNophiDTO implements Serializable { 

	private static final long serialVersionUID = 6327465606924768933L;
	
	private String id;
	private String gycbhNumber;
	private String contactName;
	private String lineId;
	private String lineName;
	private String contactPhone;
	private String statusPolicyName;
	private String inceptionDate;
	private String expiredDate;
	private BigDecimal totalPremium;
	private BigDecimal sotien;
	private String agreementId;
	private String contactId;
	private String note;
}