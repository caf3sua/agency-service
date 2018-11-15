package com.baoviet.agency.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the CATEGORY_REMINDER database table.
 * 
 */
@Getter
@Setter
public class CategoryReminderDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String code;

	private String name;
}