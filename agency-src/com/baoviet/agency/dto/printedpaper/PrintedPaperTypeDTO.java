package com.baoviet.agency.dto.printedpaper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
public class PrintedPaperTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

//	@JsonProperty("ACHI_LOAI_ANCHI_ID")
//	private Long achiLoaiAnchiId;
//	
//	@JsonProperty("DMLA_MA_LOAI_ANCHI")
//	private String dmlaMaLoaiAnchi;
//	
//	@JsonProperty("DMLA_TEN_LOAI")
//	private String dmlaTenLoai;
	
	
	@JsonProperty("PRO_CODE")
	private String productCode;
	
	@JsonProperty("PRO_NAME")
	private String productName;
}