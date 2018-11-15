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
 * The persistent class for the TL_ADD database table.
 * 
 */
@Entity
@Table(name="UPLOAD")
@Getter
@Setter
public class Upload implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "UPLOAD_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;
	
	@Column
	private String fileName;
	
	@Column
	private String fileNameEdit;
	
	@Column
	private String path;
	
	@Column
	private Integer status;
	
	@Column
	private Integer recordSuss;
	
	@Column
	private Integer recordError;
	
	@Column
	private Integer sendEmailConfirm;
	
	@Column
	private String fileNameError;
	
	@Column
	private String userImport;
	
	@Column
	private Date bvmvsSysdate;
	
	@Column
	private Date uploadDate;
	
	@Column
	private Date sendEmailDate;
	
	@Column
	private Integer type;
	
	@Column
	private String companyname;
	
	@Column
	private String departmentname;
	
	@Column
	private Date processDate;
}