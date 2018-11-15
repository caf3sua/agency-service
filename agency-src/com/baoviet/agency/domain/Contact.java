package com.baoviet.agency.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the CONTACT database table.
 * 
 */
@Entity
@Getter
@Setter
public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "CONTACT_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="CONTACT_ID", unique=true)
	private String contactId;
	
//	@OneToMany(mappedBy="contact")
//	private List<AgentReminder> reminders;

	@Column(name="COMPANY_ID")
	private String companyId;

	@Column(name="COMPANY_NAME")
	private String companyName;

	@Column(name="COMPANY_WORK")
	private String companyWork;

	@Column(name="CONTACT_CODE")
	private String contactCode;

	@Column(name="CONTACT_COMMENT")
	private String contactComment;

	@Column(name="CONTACT_NAME")
	private String contactName;

	@Column(name="CONTACT_NAME_EN")
	private String contactNameEn;

	@Column(name="CONTACT_NAME_SEARCH")
	private String contactNameSearch;

	@Column(name="CONTACT_PASSWORD")
	private String contactPassword;

	@Column(name="CONTACT_SEX")
	private String contactSex;

	@Column(name="CONTACT_SEX_NAME")
	private String contactSexName;

	@Column(name="CONTACT_USERNAME")
	private String contactUsername;

	@Column(name="COUNT_LOGIN")
	private BigDecimal countLogin;

	@Column(name="CREATED_BY_ID")
	private String createdById;

	@Column(name="CREATED_BY_NAME")
	private String createdByName;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_ISSUE")
	private Date dateOfIssue;

	@Column(name="DEPARTMENT_ID")
	private String departmentId;

	@Column(name="DEPARTMENT_NAME")
	private String departmentName;

	@Column
	private String email;

	@Column(name="HAND_PHONE")
	private String handPhone;

	@Column(name="HOME_ADDRESS")
	private String homeAddress;

	@Column(name="HOME_ADDRESS_MAIL")
	private String homeAddressMail;

	@Column(name="ID_NUMBER")
	private String idNumber;

	@Column(name="IS_DELETE")
	private String isDelete;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_DATE_LOGIN")
	private Date lastDateLogin;

	@Column
	private String occupation;

	@Column
	private String phone;

	@Column(name="PLACE_OF_ISSUE")
	private String placeOfIssue;

	@Temporal(TemporalType.DATE)
	@Column(name="SMARTAPP_SYSDATE")
	private Date smartappSysdate;

	@Column
	private String status;

	@Column
	private String type;

	@Column(name="TYPE_OF_CONTACT")
	private String typeOfContact;

	@Column(name="TYPE_OF_CONTACT_NAME")
	private String typeOfContactName;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="GROUP_TYPE")
	private String groupType;
	
	@Column(name="FACEBOOK_ID")
	private String facebookId;
}