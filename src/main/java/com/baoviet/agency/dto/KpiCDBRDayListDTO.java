package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author COMPUTER 18
 */
@Getter
@Setter
public class KpiCDBRDayListDTO implements Serializable {
	private static final long serialVersionUID = 5293803934888037950L;
	private String displayDate;
    private Date rangeStartDate;
    
}
