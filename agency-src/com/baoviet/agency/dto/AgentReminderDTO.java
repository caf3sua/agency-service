package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENT_REMINDER database table.
 * 
 */
@Getter
@Setter
public class AgentReminderDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String contactId;
	
	private String type;
	
	private String productCode;
	
	private String content;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createdDate;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date remindeDate;
	
	private String note;
	
	private String active;
	
	// More properties (no need when create)
	private String contactName; 
	
	private String productName;
	
}