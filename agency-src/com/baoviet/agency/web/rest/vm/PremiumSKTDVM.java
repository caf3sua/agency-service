package com.baoviet.agency.web.rest.vm;

import java.util.Date;

import com.baoviet.agency.utils.DateSerializer;
import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumSKTDVM {

	private String chuongTrinh;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngaysinh;

	private Boolean ngoaitruChk;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double ngoaitruPhi;

	private Boolean tncnChk;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double tncnSi;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double tncnPhi;

	private Boolean smcnChk;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double smcnSi;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double smcnPhi;

	private Boolean nhakhoaChk;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double nhakhoaPhi;
	private Boolean thaisanChk;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double thaisanPhi;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date thoihanbhTu;

	// private String agencyrole ;
	@JsonSerialize(using = DoubleSerializer.class)
	private Double qlChinhPhi;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double premiumnet;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double premiumdiscount;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double premiumdiscountsi;

	@JsonSerialize(using = DoubleSerializer.class)
	private Double premiumsktd;
}
