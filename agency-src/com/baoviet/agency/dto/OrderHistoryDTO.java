package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baoviet.agency.bean.FileContentDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the MOTO database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class OrderHistoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type;
	
	private String agreementId;

	private String gycbhNumber;
	
	private String statusPolicyName;
	
	private String title;
	
	private String content;
	
	private int order;
	
	private List<FileContentDTO> filesContent;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss dd/MM/yyyy")
	private Date hisDate;
	
	private String createDateDisplay;
}