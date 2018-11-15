package com.baoviet.agency.dto.printedpaper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * Danh sách ấn chỉ cập nhật. 
 * Mỗi ấn chỉ bao gồm thông tin:
 * 	ACHI_SO_ANCHI string: Số ấn chỉ
 * 	ACHI_STIENVN decimal: Số tiền thanh toán 
 * 	ACHI_BMBH string: khách hàng/chủ xe 
 * 	ACHI_NOTE string: ghi chú
 * 	ACHI_TUNGAY string (dd/MM/yyyy): Ngày hiệu lực từ
 * 	ACHI_DENNGAY  string (dd/MM/yyyy): Ngày hiệu lực đến
 * 	ACHI_HD_ID string: Số hóa đơn
 * 	ACHI_GCNHD string: Số giấy chứng nhận, hợp đồng
 * 	ACHI_BANGKE_THUPHI string: Số bảng kê thu phí
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class PrintedPaperUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("ACHI_SO_ANCHI")
	private String achiSoAnchi;
	
	@JsonProperty("ACHI_STIENVN")
	private Double achiStienvn;
	
	@JsonProperty("ACHI_BMBH")
	private String achiBmbh;
	
	@JsonProperty("ACHI_NOTE")
	private String achiNote;
	
	@JsonProperty("ACHI_TUNGAY") 
	private String achiTungay; // (dd/MM/yyyy): Ngày hiệu lực từ
	
	@JsonProperty("ACHI_DENNGAY")
	private String achiDenngay; // (dd/MM/yyyy): Ngày hiệu lực đến
	
	@JsonProperty("ACHI_HD_ID")
	private String achiHdId;
	
	@JsonProperty("ACHI_BANGKE_THUPHI")
	private String achiBangkeThuphi;

	@JsonProperty("ACHI_GCNHD")
	private String achiGcnhd;
}