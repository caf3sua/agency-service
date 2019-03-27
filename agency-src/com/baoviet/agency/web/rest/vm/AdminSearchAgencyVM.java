package com.baoviet.agency.web.rest.vm;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class AdminSearchAgencyVM {
	
    private String departmentId;

    private String keyword;
	
	private Integer numberRecord;
}
