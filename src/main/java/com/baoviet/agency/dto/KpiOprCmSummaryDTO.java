package com.baoviet.agency.dto;

import java.util.Date;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: Nam, Nguyen Hoai
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KpiOprCmSummaryDTO extends BaseDTO {
    public static final Logger LOG = Logger.getLogger(KpiOprCmSummaryDTO.class);

    private String dayIdStr;

    private Date dayId;

    private String crType;

    private String areaName;

    private String areaCode;

    private String provinceName;

    private String provinceCode;

    private String districtName;

    private String districtCode;

    private Long unitId;

    private String unitCode;

    private Long topUnitId;

    private String topUnitCode;

    private Long totalCr;

    private Long completedCr;

    private Long reduceTotalCr;

    private Long reduceCompletedCr;

    // More data
    private String regionCode;
    private String regionName;
    private Double completedRate;
    private Double target;
    private Long outOfDateCr;
    private String currentLv;
    
    private String index;
    private String total_wo_tmp;
    private String completed_rate_tmp;
    private String over_tmp;
    private String completed_wo_tmp;
    private String wmqyNumber;
    private Double avgWeek;
    private String startDate;
    private String endDate;
    
    private String listTotalCR;
    private String listCompletedCR;
    private String listTarget;
    private String listTargetNullCount;
    private String listRateCR;
    
    public double getBeforeLatestRate() {
        if(listRateCR != null) {
            String[] temps = listRateCR.split(";");
            if(temps != null && temps.length > 1) {
                try{
                    String latestStr = temps[temps.length - 2];
                    return Double.parseDouble(latestStr);
                }catch(Exception ex) {
                    LOG.error(ex);
                    return 0;
                }
            }
        }
        return 0;
    }
    
    public double getLatestRate() {
        if(listRateCR != null) {
            String[] temps = listRateCR.split(";");
            if(temps != null && temps.length > 0) {
                try{
                    String latestStr = temps[temps.length - 1];
                    return Double.parseDouble(latestStr);
                }catch(Exception ex) {
                    LOG.error(ex);
                    return 0;
                }
            }
        }
        return 0;
    }
    
    public int[] getStatusDone() {
        if (listRateCR != null && listTarget != null){
            String[] rate = listRateCR.split(";");
            String[] target = listTarget.split(";");
            int[] resvals = new int[rate.length];
            int index = 0;
            for(String item : rate) {
                try{
                    if (Double.valueOf(item).doubleValue() >= Double.valueOf(target[index]).doubleValue()){
                        resvals[index] = 1;
                    }
                    else{
                        resvals[index] = 0;
                    }
                }catch(Exception ex) {
                    LOG.error(ex);
                }
                index ++;
            }
            return resvals;
        }
        else{
            int[] resvals = new int[0];
            return resvals;
        }
    }
    
}
