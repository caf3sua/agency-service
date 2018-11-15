package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the SktdRate database table.
 * 
 */
@Entity
@Table(name="SKTD_RATE")
@Getter
@Setter
public class SktdRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	private String id;

	@Column
	private String chuongTrinh;

	@Column
	private Integer tuoiTu;

	@Column
	private Integer tuoiDen;

	@Column
	private Double qlChinh;

	@Column
	private Double qlNgoaiTru;

	@Column
	private Double qlTncn;

	@Column
	private Double qlSmcn;

	@Column
	private Double qlNhakhoa;

	@Column
	private Double qlThaiSan;

	@Override
	public String toString() {
		return "SktdRate [id=" + id + ", chuongTrinh=" + chuongTrinh + ", tuoiTu=" + tuoiTu + ", tuoiDen=" + tuoiDen
				+ ", qlChinh=" + qlChinh + ", qlNgoaiTru=" + qlNgoaiTru + ", qlTncn=" + qlTncn + ", qlSmcn=" + qlSmcn
				+ ", qlNhakhoa=" + qlNhakhoa + ", qlThaiSan=" + qlThaiSan + "]";
	}
	
}