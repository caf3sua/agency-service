package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the ANCHI database table.
 * 
 */
@Entity
@Table(name="ANCHI")
@Getter
@Setter
public class Anchi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "ANCHI_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ANCHI_ID", unique=true)
	private String anchiId;
	
	@Column(name="ACHI_SO_ANCHI")
	private String achiSoAnchi;
	
	@Column(name="ACHI_HD_ID")
	private String achiHdId;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="ACHI_TUNGAY")
	private Date achiTungay;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="ACHI_DENNGAY")
	private Date achiDenngay;
	
	@Column(name="ACHI_TINHTRANG_CAP")
	private String achiTinhtrangCap;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="ACHI_NGAYCAP")
	private Date achiNgaycap;
	
	@Column(name="ACHI_PHIBH")
	private double achiPhibh;
	
	@Column(name="ACHI_STIENVN")
	private double achiStienvn;

	@Lob
	@Column(name="IMG_GCN")
	private byte[] imgGcn;
	
	@Lob
	@Column(name="IMG_GYCBH")
	private byte[] imgGycbh;
	
	@Lob
	@Column(name="IMG_HD")
	private byte[] imgHd;

	@Column(name="MCI_ADD_ID")
	private String mciAddId;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="POLICY_NUMBER")
	private String policyNumber;
	
	@Column(name="QLHA_ID")
	private String qlhaId;
	
	@Column(name="INSUREJ_URN")
	private String insurejUrn;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="BVSYSDATE")
	private Date bvsysdate;
	
	@Column(name="CREATE_USER")
	private String createUser;
	
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="MODIFY_DATE")
	private Date modifyDate;
	
	@Column(name="MODIFY_USER")
	private String modifyUser;
	
	@Column(name="ACHI_MA_ANCHI")
	private String achiMaAnchi;
	
	@Column(name="ACHI_TEN_ANCHI")
	private String achiTenAnchi;
	
	@Column(name="ACHI_DONVI")
	private String achiDonvi;
	
	@Column(name="LINE_ID")
	private String lineId;

	@Column(name="CONTACT_ID")
	private String contactId;
}