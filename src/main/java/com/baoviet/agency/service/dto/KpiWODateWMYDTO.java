package com.baoviet.agency.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Nam, Nguyen Hoai
 */
@Getter
@Setter
@RequiredArgsConstructor
public class KpiWODateWMYDTO {

    private String fromDate;
    private String toDate;
    private String startDate;
    private String endDate;
}
