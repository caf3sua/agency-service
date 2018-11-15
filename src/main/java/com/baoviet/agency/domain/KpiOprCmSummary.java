package com.baoviet.agency.domain;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import com.baoviet.agency.dto.KpiCDBRDayListDTO;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.KpiOprCm12DTO;
import com.baoviet.agency.dto.KpiOprCmSummaryDTO;
import com.baoviet.agency.dto.KpiTargetDTO;
import com.baoviet.agency.service.dto.KpiWODateWMYDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
//@Embeddable
@Table(name = "KPI_OPR_CM_SUMMARY")
@SqlResultSetMappings({
	@SqlResultSetMapping(
	        name = "DateWMYMapping",
	        classes = @ConstructorResult(
	                targetClass = KpiWODateWMYDTO.class,
	                columns = {
	                    @ColumnResult(name = "fromDate"),
	                    @ColumnResult(name = "toDate"),
	                    @ColumnResult(name = "startDate"),
	                    @ColumnResult(name = "endDate")
	                })),
	@SqlResultSetMapping(
	        name = "KpiOprCmSummaryDTOMapping",
	        classes = @ConstructorResult(
	                targetClass = KpiOprCmSummaryDTO.class,
	                columns = {
	                    @ColumnResult(name = "regionCode"),
	                    @ColumnResult(name = "regionName"),
	                    @ColumnResult(name = "totalCr", type = Long.class),
	                    @ColumnResult(name = "completedCr", type = Long.class),
	                    @ColumnResult(name = "completedRate", type = Double.class),
	                    @ColumnResult(name = "target", type = Double.class),
	                    @ColumnResult(name = "outOfDateCr", type = Long.class),
	                    @ColumnResult(name = "dayIdStr"),
	                    @ColumnResult(name = "listTotalCR"),
	                    @ColumnResult(name = "listCompletedCR"),
	                    @ColumnResult(name = "listTarget"),
	                    @ColumnResult(name = "listTargetNullCount"),
	                    @ColumnResult(name = "listRateCR")
	                })),
	@SqlResultSetMapping(
	        name = "KpiOprCm11DTOMapping",
	        classes = @ConstructorResult(
	                targetClass = AgencyDTO.class,
	                columns = {
	                    @ColumnResult(name = "crNumber"),
	                    @ColumnResult(name = "crId", type = Long.class),
	                    @ColumnResult(name = "reduceStatus"),
	                    @ColumnResult(name = "crType"),
	                    @ColumnResult(name = "codeReduce"),
	                    @ColumnResult(name = "dutyType"),
	                    @ColumnResult(name = "handleUserName"),
	                    @ColumnResult(name = "state"),
	                    @ColumnResult(name = "startTimeSchedule", type = Timestamp.class),
	                    @ColumnResult(name = "endTimeSchedule", type = Timestamp.class),
	                    @ColumnResult(name = "changeDate", type = Timestamp.class)
	                })),
	@SqlResultSetMapping(
	        name = "KpiOprCm12DTOMapping",
	        classes = @ConstructorResult(
	                targetClass = KpiOprCm12DTO.class,
	                columns = {
	                    @ColumnResult(name = "crNumber"),
	                    @ColumnResult(name = "crId", type = Long.class),
	                    @ColumnResult(name = "reduceStatus"),
	                    @ColumnResult(name = "crType"),
	                    @ColumnResult(name = "codeReduce"),
	                    @ColumnResult(name = "dutyType"),
	                    @ColumnResult(name = "handleUserName"),
	                    @ColumnResult(name = "state"),
	                    @ColumnResult(name = "startTimeScheduleDate"),
	                    @ColumnResult(name = "changeDateDate"),
	                    @ColumnResult(name = "closeDateDate")
	                })),
	@SqlResultSetMapping(
	        name = "KpiTargetDTOMapping",
	        classes = @ConstructorResult(
	                targetClass = KpiTargetDTO.class,
	                columns = {
	                    @ColumnResult(name = "locationCode"),
	                    @ColumnResult(name = "rangeStartDate"),
	                    @ColumnResult(name = "targetValueMonthly")
	                })),
	@SqlResultSetMapping(
	        name = "KpiCDBRDayListDTOMapping",
	        classes = @ConstructorResult(
	                targetClass = KpiCDBRDayListDTO.class,
	                columns = {
	                    @ColumnResult(name = "displayDate"),
	                    @ColumnResult(name = "rangeStartDate", type = Date.class)
	                }))
})
/** 
* 
* @author: Nam, Nguyen Hoai 
*/
@Getter
@Setter
public class KpiOprCmSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "DAY_ID") 
    private Date dayId;
	
    @Column(name = "CR_TYPE") 
    private String crType;
    
    @Column(name = "AREA_NAME") 
    private String areaName;
    
    @Column(name = "AREA_CODE") 
    private String areaCode;
    
    @Column(name = "PROVINCE_NAME") 
    private String provinceName;
    
    @Column(name = "PROVINCE_CODE") 
    private String provinceCode;
    
    @Column(name = "DISTRICT_NAME") 
    private String districtName;
    
    @Column(name = "DISTRICT_CODE") 
    private String districtCode;
    
    @Column(name = "UNIT_ID") 
    private Long unitId;
    
    @Column(name = "UNIT_CODE") 
    private String unitCode;
    
    @Column(name = "TOP_UNIT_ID") 
    private Long topUnitId;
    
    @Column(name = "TOP_UNIT_CODE") 
    private String topUnitCode;
    
    @Column(name = "TOTAL_CR") 
    private Long totalCr;
    
    @Column(name = "COMPLETED_CR") 
    private Long completedCr;
    
    @Column(name = "REDUCE_TOTAL_CR") 
    private Long reduceTotalCr;
    
    @Column(name = "REDUCE_COMPLETED_CR") 
    private Long reduceCompletedCr;
}
