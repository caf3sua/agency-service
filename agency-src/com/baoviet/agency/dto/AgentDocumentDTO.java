package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENT_DOCUMENT database table.
 * 
 */
@Getter
@Setter
public class AgentDocumentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String code;

	private String name;
	
	private String fileName;
	
	private String type;
	
	private String note;
	
	private Long numberDownload;
	
	private String usernameUpload;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateUpload;
	
	private String contentFile;
}