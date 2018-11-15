package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the KCARE_BENEFIT database table.
 * 
 */
@Entity
@Table(name="KCARE_BENEFIT")
@Getter
@Setter
public class KcareBenefit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="KCARE_BENEFIT_ID", unique=true)
	private String kcareBenefitId;

	@Column(name="PLAN_ID")
	private String planId;

	@Column(name="PLAN_NAME")
	private String planName;

	@Column(name="SI_CHET_TAI_NAN")
	private Double siChetTaiNan;

	@Column(name="SI_CHET_UNG_THU")
	private Double siChetUngThu;

	@Column(name="SI_TCNV")
	private Double siTcnv;

	@Column(name="SI_TCNV_1")
	private Double siTcnv1;

	@Column(name="SI_TCNV_2")
	private Double siTcnv2;

	@Column(name="SI_UNG_THU")
	private Double siUngThu;

	@Column(name="SI_UNG_THU_1")
	private Double siUngThu1;

	@Column(name="SI_UNG_THU_2")
	private Double siUngThu2;

	@Column
	private Double suminsured;

}