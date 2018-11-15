package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the FILES database table.
 * 
 */
@Entity
@Table(name="FILES")
@Getter
@Setter
public class Files implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "FILES_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="FILE_ID", unique=true)
	private String fileId;

	@Column(name="AGENT_ID")
	private String agentId;

	@Column(name="AGENT_NAME")
	private String agentName;

	@Column(name="BANK_ID")
	private String bankId;

	@Column(name="BANK_NAME")
	private String bankName;

	@Column(name="BAOVIET_COMPANY_ID")
	private String baovietCompanyId;

	@Column(name="BAOVIET_COMPANY_NAME")
	private String baovietCompanyName;

	@Column(name="BAOVIET_DEPARTMENT_ID")
	private String baovietDepartmentId;

	@Column(name="BAOVIET_DEPARTMENT_NAME")
	private String baovietDepartmentName;

	@Column(name="BAOVIET_USER_ID")
	private String baovietUserId;

	@Column(name="BAOVIET_USER_NAME")
	private String baovietUserName;

	@Lob
	private byte[] content;

	@Column(name="FULL_NAME")
	private String fullName;

	@Column(name="GYCBH_ID")
	private String gycbhId;

	@Column
	private Double length;

	@Column(name="LINE_ID")
	private String lineId;

	@Column(name="LINE_NAME")
	private String lineName;

	private String name;

	@Column(name="TEAM_ID")
	private String teamId;

	@Column(name="TEAM_NAME")
	private String teamName;

	@Column
	private String type;

	@Column(name="TYPE_OF_DOCUMENT")
	private String typeOfDocument;

	@Temporal(TemporalType.DATE)
	@Column(name="UPLOAD_DATE")
	private Date uploadDate;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;

}