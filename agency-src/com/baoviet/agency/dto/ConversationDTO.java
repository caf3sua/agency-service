package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the FILES database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ConversationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String conversationId;

	private String conversationContent;

	private String parrentId;

	private String lineId;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date sendDate;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date responseDate;

	private String userId;

	private String userName;

	private String role;

	private String sendEmail;

	private String conversationBlobString;	// Blob

	private String mailStatus;
	
	private String title;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createDate;
}