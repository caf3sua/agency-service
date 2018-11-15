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
 * The persistent class for the ProductGenInfoDTO database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ProductGenInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String productGenInfoId;
	
	private String fkProductGenId;
	
	private String title;
	
	private String status;
	
	private String contentProduct;
	
	private String isDefault;
	
	private String isShowMenu;
	
	private String urlRedirect;
	
	private String sort;
	
	private String description;
	
	private String fkProductGenIds;
	
	private String fkProductGenCatId;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date issueDate;
}