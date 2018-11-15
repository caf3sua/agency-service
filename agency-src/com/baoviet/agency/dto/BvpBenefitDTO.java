package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP_BENEFIT database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BvpBenefitDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String bvpBenefitId;

	private String chuongTrinh;

	private String chuongTrinhId;

	private Double stbhCtc;

	private Double stbhQlbs1;

	private Double stbhQlbs4;

	private Double stbhQlbsTs;
}