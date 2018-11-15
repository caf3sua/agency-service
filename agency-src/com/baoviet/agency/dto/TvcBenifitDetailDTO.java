package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TVC_BENIFIT_DETAIL database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TvcBenifitDetailDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String a;

	private String a1;

	private String a2;

	private String b;

	private String c;

	private String name;

}