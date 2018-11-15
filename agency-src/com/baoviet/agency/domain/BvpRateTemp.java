package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP_RATE_TEMP database table.
 * 
 */
@Entity
@Table(name="BVP_RATE_TEMP")
@Getter
@Setter
public class BvpRateTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	private String id;

	@Column(name="CHUONG_TRINH")
	private String chuongTrinh;

	@Column(name="QL_CHINH")
	private Double qlChinh;

	@Column(name="QL_NGOAI_TRU")
	private Double qlNgoaiTru;

	@Column(name="QL_NHA_KHOA")
	private Double qlNhaKhoa;

	@Column(name="QL_SMCN")
	private Double qlSmcn;

	@Column(name="QL_TNCN")
	private Double qlTncn;

	@Column(name="TUOI_DEN")
	private Integer tuoiDen;

	@Column(name="TUOI_TU")
	private Integer tuoiTu;
}