package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MOTO database table.
 * 
 */
@Entity
@Table(name="MOTO_HONDA_CAT")
@Getter
@Setter
public class MotoHondaCat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	private String id;
	
	@Column(name="MAC_XE")
	private String macXe;
	
	@Column(name="MAU_XE")
	private String mauXe;
	
	@Column(name="DUNG_TICH")
	private String dungTich;
	
	@Column(name="HE_THONG_PHANH")
	private String heThongPhanh;
	
	@Column(name="GIA_TRI_XE")
	private Double giaTriXe;
	
	@Column(name="SO_NAM")
	private String soNam;
	
	@Column(name="TEN_HIEN_THI")
	private String tenHienThi;

}