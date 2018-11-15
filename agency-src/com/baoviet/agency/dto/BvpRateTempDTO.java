package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP_RATE_TEMP database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BvpRateTempDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String chuongTrinh;

	private Double qlChinh;

	private Double qlNgoaiTru;

	private Double qlNhaKhoa;

	private Double qlSmcn;

	private Double qlTncn;

	private Integer tuoiDen;

	private Integer tuoiTu;
}