package com.baoviet.agency.dto.printedpaper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * Danh sách ấn chỉ. 
 * Mỗi ấn chỉ bao gồm thông tin:
 * 	ACHI_SO_ANCHI: Số ấn chỉ
 * 	ACHI_CTY_ID: Mã đơn vị
 * 	OUTLET_NAME: Tên đơn vị
 * 	ACHI_HD_ID: Mã hợp đồng
 * 	ACHI_PHIBH: Phí bảo hiểm
 * 	ACHI_STIENVN: Số tiền thanh toán
 * 	ACHI_SERI: Số seri ấn chỉ
 * 	ACHI_DLQL_ID: Mã đại lý
 * 	ACHI_BMBH: Bên mua bảo hiểm
 * 	ACHI_KYHIEU_ANCHI_ID: Mã ấn chỉ
 * 	STATUS: Trạng thái ấn chỉ
 * 	ACHI_TEN_ANCHI: Tên ấn chỉ
 * 	ACHI_DLQL_NAME: Tên đại lý
 *  PRO_CODE: Mã sản phẩm
 */
@Getter
@Setter
public class PrintedPaperDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@JsonProperty("ACHI_GCNHD")
	private String achiGcnhd;
	
	@JsonProperty("ACHI_MAVACH")
	private String achiMavach;
	
	@JsonProperty("ACHI_LOAI_ANCHI_ID")
	private Long achiLoaiAnchiId;
	
	@JsonProperty("ACHI_SO_ANCHI")
	private String achiSoAnchi;
	
	@JsonProperty("ACHI_CTY_ID")
	private String achiCtyId;
	
	@JsonProperty("OUTLET_NAME")
	private String outletName;
	
	@JsonProperty("ACHI_HD_ID")
	private String achiHdId;
	
	@JsonProperty("ACHI_PHIBH")
	private Double achiPhibh;

	@JsonProperty("ACHI_STIENVN")
	private Double achiStienvn;
	
	@JsonProperty("ACHI_SERI")
	private String achiSeri;
	
	@JsonProperty("ACHI_DLQL_ID")
	private String achiDlqlId;

	@JsonProperty("ACHI_BMBH")
	private String achiBmbh;

	@JsonProperty("ACHI_KYHIEU_ANCHI_ID")
	private String achiKyhieuAnchiId;
	
	@JsonProperty("STATUS")
	private String status;
	
	@JsonProperty("ACHI_TEN_ANCHI")
	private String achiTenAnchi;
	
	@JsonProperty("ACHI_USER_UPDATED")
	private String achiUserUpdated;

	@JsonProperty("ACHI_LAST_UPDATED")
	private String achiLastUpdated;
	
	@JsonProperty("ACHI_DLQL_NAME")
	private String achiDlqlName;
	
	@JsonProperty("PRO_CODE")
	private String productCode;
	
	private boolean anchiUsing;
}