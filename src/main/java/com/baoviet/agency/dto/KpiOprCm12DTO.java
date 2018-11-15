package com.baoviet.agency.dto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: Nam, Nguyen Hoai
 */
@Getter
@Setter
public class KpiOprCm12DTO extends BaseDTO {

    private Long crId;

    private String crProcessName;

    private String crNumber;

    private String title;

    private Timestamp startTimeSchedule;

    private Timestamp endTimeSchedule;

    private Timestamp createdDate;

    private String createUnitName;

    private String pathCreateUnitName;

    private String createUserName;

    private String handleUnitName;

    private String pathHandleUnitName;

    private String handleUserName;

    private Long closeUnitId;

    private String closeUnitCode;

    private String closeUnitName;

    private String crType;

    private String syName;

    private String risk;

    private String impactSegmentName;

    private String dutyType;

    private String impactCharacteristic;

    private String state;

    private Date changeDate;

    private Date closeDate;

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

    // Temp
    private String startTimeScheduleDate;
    private String changeDateDate;
    private String closeDateDate;
}
