package com.baoviet.agency.dto.momo.component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListSelectorComponent extends BaseComponent implements Serializable {
private static final long serialVersionUID = 1L;
	
	public static final String PROPERTY_PLACEHOLDER = "placeholder";
	
	public static final String PROPERTY_TITLE = "title";
	
	@NotEmpty
	private String type = "listSelector";
	
	private List<ItemOptionComponent> value;
	
	@NotEmpty
	private List<ItemOptionComponent> items;
	
	private String errorMessage;
	
	private Map<String, String> properties;
	
}
