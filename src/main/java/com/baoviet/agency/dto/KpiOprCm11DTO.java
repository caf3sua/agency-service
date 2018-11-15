/* 
* Copyright 2011 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
*/
package com.baoviet.agency.dto;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

/** 
* 
* @author: Nam, Nguyen Hoai 
*/
@Getter
@Setter
public class KpiOprCm11DTO extends BaseDTO { 

	private Long crId;
	
	private String crProcessName;
	
	private String crNumber;
	
	private String title;
	
	private Timestamp earliestStartTime;
	
	private Timestamp latestStartTime;
	
	private Timestamp startTimeSchedule;
	
	private Timestamp endTimeSchedule;
	
	private Timestamp createdDate;
	
	private String createUnitName;
	
	private String pathCreateUnitName;
	
	private String createUserName;
	
	private Long handleUnitId;
	
	private String handleUnitCode;
	
	private String handleUnitName;
	
	private String pathHandleUnitName;
	
	private String handleUserName;
	
	private String crType;

	private String syName;
	
	private String risk;
	
	private String impactSegmentName;
	
	private String dutyType;
	
	private String impactCharacteristic;
	
	private String state;
	
	private Timestamp changeDate;
	
	private String stateKpi;
	
	private String deviceTypeName;
	
	private String areaCode;
	
	private String areaName;
	
	private String provinceCode;
	
	private String provinceName;
	
	private String districtCode;
	
	private String districtName;
	
	private Long topUnitId;
	
	private String topUnitCode;
}