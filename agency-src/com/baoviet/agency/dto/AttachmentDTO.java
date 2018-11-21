package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the ATTACHMENT database table.
 * 
 */
@Getter
@Setter
public class AttachmentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String attachmentId;

	private String contentFile;

	private String parrentId;

	private Double contentLeng;

	private String attachmentName;

	private Date createDate;

	private Date modifyDate;

	private String userId;

	private Date tradeolSysdate;

	private Integer istransferred;
	
	private String attachmentType;
}