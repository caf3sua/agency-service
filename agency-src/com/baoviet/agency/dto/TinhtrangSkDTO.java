package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TINHTRANG_SK database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class TinhtrangSkDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	@NotNull
	@ApiModelProperty(value = "Tên, địa chỉ bác sĩ/bệnh viện điều trị", required = true)
	private String benhvienorbacsy;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date bvSysdate;

	@NotNull
	@ApiModelProperty(value = "Chi tiết điều trị", required = true)
	private String chitietdieutri;

	@NotNull
	@ApiModelProperty(value = "Chuẩn đoán", required = true)
	private String chuandoan;

	private String congtybh;

	private String dkdacbiet;

	private String idThamchieu;

	@NotNull
	@ApiModelProperty(value = "Kết quả", required = true)
	private String ketqua;

	private String khuoctu;

	private String lydodc;

	private String lydoycbt;

	private String masanpham;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngaybatdau;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull
	@ApiModelProperty(value = "Ngày khám/điều trị", required = true)
	private Date ngaydieutri;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayhethan;

	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date ngayycbt;

	private String questionThamchieu;

	private String sohd;

	private Double sotienbh;

	private Double sotienycbt;
}