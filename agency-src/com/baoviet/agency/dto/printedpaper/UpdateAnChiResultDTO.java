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
 * 	{
 *     "Status": "0",
 *     "ACHI_USER_UPDATED": "agency",
 *     "ACHI_LAST_UPDATED": "7/19/2018 10:59:31 AM",
 *     "ACHI_SO_ANCHI": "0311TNG/18-0000002010"
 *  }
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class UpdateAnChiResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("ACHI_USER_UPDATED")
	private String achiUserUpdated;
	
	@JsonProperty("ACHI_LAST_UPDATED")
	private String achiLastUpdated;
	
	@JsonProperty("ACHI_SO_ANCHI")
	private String achiSoAnchi;
}