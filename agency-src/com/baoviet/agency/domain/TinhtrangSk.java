package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TINHTRANG_SK database table.
 * 
 */
@Entity
@Table(name="TINHTRANG_SK")
@Getter
@Setter
public class TinhtrangSk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TINHTRANG_SK_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;

	@Column
	private String benhvienorbacsy;

	@Temporal(TemporalType.DATE)
	@Column(name="BV_SYSDATE")
	private Date bvSysdate;

	@Column
	private String chitietdieutri;

	@Column
	private String chuandoan;

	@Column
	private String congtybh;

	@Column
	private String dkdacbiet;

	@Column(name="ID_THAMCHIEU")
	private String idThamchieu;

	@Column
	private String ketqua;

	@Column
	private String khuoctu;

	@Column
	private String lydodc;

	@Column
	private String lydoycbt;

	@Column
	private String masanpham;

	@Temporal(TemporalType.DATE)
	private Date ngaybatdau;

	@Temporal(TemporalType.DATE)
	private Date ngaydieutri;

	@Temporal(TemporalType.DATE)
	private Date ngayhethan;

	@Temporal(TemporalType.DATE)
	private Date ngayycbt;

	@Column(name="QUESTION_THAMCHIEU")
	private String questionThamchieu;

	@Column
	private String sohd;

	@Column
	private Double sotienbh;

	@Column
	private Double sotienycbt;
}