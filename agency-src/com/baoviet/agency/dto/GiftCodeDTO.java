package com.baoviet.agency.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class GiftCodeDTO {
	private String id;
	private String giftCode;
	private Date fromDate;
	private Date toDate;
	private String active;
	private String proType;
	private Integer discount;
	private String email;
	private String description;
	private String lineId;
	private String idNum;
	private Integer name;
}
