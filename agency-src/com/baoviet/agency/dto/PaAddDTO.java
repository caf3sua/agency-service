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
 * The persistent class for the PA_ADD database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class PaAddDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String paAddId;

	private String address;

	private String cellPhone;

	private String city;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dob;

	private String emailAdress;

	private String homePhone;

	private String idPasswport;

	private String insuredName;

	private String paId;

	private Double premium;

	private String relationship;

	private Double si;

	private String title;
}