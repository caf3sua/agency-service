package com.baoviet.agency.dto.momo.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class MomoComponent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String PROPERTY_PLACEHOLDER = "placeholder";
	
	public static final String PROPERTY_TITLE = "title";
	
	public static final String PROPERTY_INPUT_FORMAT = "inputFormat";
	
	public static final String PROPERTY_OUTPUT_FORMAT = "outputFormat";
	
	public static final String COMPONENT_TYPE_TEXT_VIEW = "text";
	public static final String COMPONENT_TYPE_TEXT_INPUT = "textInput";
	public static final String COMPONENT_TYPE_CALENDAR_PICKER = "calendarPicker";
	public static final String COMPONENT_TYPE_RADIO_GROUP = "radioGroup";
	public static final String COMPONENT_TYPE_CHECK_LIST = "checkList";
	public static final String COMPONENT_TYPE_CHECK_BOX = "checkBox";
	public static final String COMPONENT_TYPE_LIST_SELECTOR = "listSelector";
	public static final String COMPONENT_TYPE_BUTTON = "submit";
	public static final String COMPONENT_MULTI_LIST_SELECTOR = "multiListSelector";
	public static final String COMPONENT_STEP_INDICATOR = "stepIndicator";
	public static final String COMPONENT_TABLE = "table";
	
	@NotEmpty
	private String id;
	
	@NotEmpty
	private String type;
	
//	private String value;
//	private List<ItemOptionComponent> value;
	
	private Object value;
	
	@ApiModelProperty(value = "Auto fill value of user into field", allowableValues = "email,password,address,phoneNumber")
	private String autoFill;
	
	private Boolean isChecked;
	
	private String text;
	
	private String title;
	
	private List<ItemOptionComponent> items;
	
	private String errorMessage;
	
	private Map<String, Object> properties = new HashMap<>();
	
	private List<String> bindId;
	
	private StyleComponent style;
	
	private List<SelectorComponent> selectorComponent;
	
	private SelectorComponent selector;
	
	private List<List<ItemOptionComponent>> rows;
	
}
