package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP_BENEFIT database table.
 * 
 */
@Entity
@Table(name="BVP_BENEFIT")
@Getter
@Setter
public class BvpBenefit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BVP_BENEFIT_ID", unique=true)
	private String bvpBenefitId;

	@Column(name="CHUONG_TRINH")
	private String chuongTrinh;

	@Column(name="CHUONG_TRINH_ID")
	private String chuongTrinhId;

	@Column(name="STBH_CTC")
	private Double stbhCtc;

	@Column(name="STBH_QLBS_1")
	private Double stbhQlbs1;

	@Column(name="STBH_QLBS_4")
	private Double stbhQlbs4;

	@Column(name="STBH_QLBS_TS")
	private Double stbhQlbsTs;
}