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
 * The persistent class for the TVC_PLANE_ADD database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TvcPlaneAddDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tvcPlaneAddId;

	private String address;

	private Integer age;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date bvSysdate;

	private String cellPhone;

	private Double changePremium;

	private String city;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dob;

	private String emailAdress;

	private String homePhone;

	private String idPasswport;

	private String insuredName;

	private Double netPremium;

	private String relationshipId;

	private String relationshipName;

	private String sexId;

	private String sexName;

	private String title;

	private Double totalPremium;

	private String tvcPlaneId;
}