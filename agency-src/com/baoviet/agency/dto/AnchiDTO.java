package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the ANCHI database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class AnchiDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String anchiId;

	private String achiSoAnchi;

	private String achiHdId;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date achiTungay;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date achiDenngay;

	private String achiTinhtrangCap;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date achiNgaycap;

	private double achiPhibh;

	private double achiStienvn;

	private String imgGcnContent;

	private String imgGycbhContent;

	private String imgHdContent;

	private String mciAddId;

	private String status;

	private String policyNumber;

	private String qlhaId;

	private String insurejUrn;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date bvsysdate;

	private String createUser;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date modifyDate;

	private String modifyUser;

	private String achiMaAnchi;

	private String achiTenAnchi;

	private String achiDonvi;
	
	private String lineId;
	
	private String contactId;
}