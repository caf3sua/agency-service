package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MENU database table.
 * 
 */
@Entity
@Table(name="GRAB_GIFTCODE")
@Getter
@Setter
public class GrabGiftCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "GRAB_GIFTCODE_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="GRAB_ID", unique=true)
	private String grabId;
	
	@Column(name="GRAB_CODE")
	private String grabCode;
		
	@Column(name="GRAB_EXPORTDATE")
	private Date grabExportdate;
	
	@Column(name="GIFTCODE_CODE")
	private String giftcodeCode;
	
	@Column(name="CONTACT_ID")
	private String contactId;
	
	@Column(name="CONTACT_PHONE")
	private String contactPhone;
	
	@Column(name="TO_DATE")
	private Date toDate;
	
	@Column(name="BV_SYSDATE")
	private Date bvSysdate;
	
	@Column(name="MCI_ADD_ID")
	private String mciAddId;
	
	@Column(name="CONTACT_NAME")
	private String contactName;
}