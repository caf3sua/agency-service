package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TRAVELCARE_BENIFIT database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TravelcareBenifitDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String eur;

	private String nameEn;

	private String nameVi;

	private String no;

	private String plan;

	private String planNameEn;

	private String planNameVi;

	private String usd;

	private String vnd;
}