package com.baoviet.agency.dto.momo.component;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarPickerComponent extends BaseComponent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String PROPERTY_PLACEHOLDER = "placeholder";
	
	public static final String PROPERTY_TITLE = "title";
	
	public static final String PROPERTY_INPUT_FORMAT = "inputFormat";
	
	public static final String PROPERTY_OUTPUT_FORMAT = "outputFormat";
	
	@NotEmpty
	private String type = "calendarPicker";
	
	private String value;
	
	private Map<String, String> properties;
}
