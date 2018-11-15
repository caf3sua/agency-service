package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the SMS_SEND database table.
 * 
 */
@Getter
@Setter
public class SmsSendDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String smsId;

	private String userId;

	private String userName;

	private String phoneNumber;

	private String content;

	private Date smsSysdate;

	private String filename;

	private Integer numberSuccess;

	private Integer numberFails;

	private String fullname;

	private String companyid;

	private String departmentid;

	private String companyname;

	private String departmentname;
}