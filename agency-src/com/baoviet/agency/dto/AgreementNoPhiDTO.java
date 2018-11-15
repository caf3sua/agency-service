package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the AGREEMENT database table.
 * 
 */
@Getter
@Setter
public class AgreementNoPhiDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String agreementId;

	private String contactId;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createDate;

	private String note;

	private double sotien;
}