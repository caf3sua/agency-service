package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the KCARE_BENEFIT database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class KcareBenefitDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String kcareBenefitId;

	private String planId;

	private String planName;

	private Double siChetTaiNan;

	private Double siChetUngThu;

	private Double siTcnv;

	private Double siTcnv1;

	private Double siTcnv2;

	private Double siUngThu;

	private Double siUngThu1;

	private Double siUngThu2;

	private Double suminsured;

}