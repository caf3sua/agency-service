package com.baoviet.agency.dto.momo.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ItemOptionComponent {
	private String text;
	
	private String value;
	
	private String tooltip;
	
	private StyleComponent style;

	public ItemOptionComponent(String text, String value, String tooltip) {
		super();
		this.text = text;
		this.value = value;
		this.tooltip = tooltip;
	}
	
	public ItemOptionComponent(String text, String value) {
		super();
		this.text = text;
		this.value = value;
	}
	
	public ItemOptionComponent() {
	}
}
