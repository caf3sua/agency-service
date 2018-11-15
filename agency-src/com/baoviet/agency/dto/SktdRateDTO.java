package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the SktdRate database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class SktdRateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String chuongTrinh;

	private Integer tuoiTu;

	private Integer tuoiDen;

	private Double qlChinh;

	private Double qlNgoaiTru;

	private Double qlTncn;

	private Double qlSmcn;

	private Double qlNhakhoa;

	private Double qlThaiSan;
}