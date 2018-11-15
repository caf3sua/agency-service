package com.baoviet.agency.dto.momo.component;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class SelectorComponent {

	private String text;
	
	private String value;
	
	private String childrenTitle;
	
	private List<SelectorComponent> children;
	
	public SelectorComponent() {
		super();
	}

	public SelectorComponent(String text, String value, String childrenTitle, List<SelectorComponent> children) {
		super();
		this.text = text;
		this.value = value;
		this.childrenTitle = childrenTitle;
		this.children = children;
	}

		
}
