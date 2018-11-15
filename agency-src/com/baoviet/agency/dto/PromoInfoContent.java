package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TL_ADD database table.
 * 
 */

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class PromoInfoContent implements Serializable {
	private static final long serialVersionUID = 1L;

	private String newID;
	private String url;
	private String title;
	private String fromDate;
	private String toDate;
	private String displayDate;
	
	private byte[] content;
}