package com.baoviet.agency.dto.momo.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class StyleComponent {

	private String fontWeight;
	
	private Integer paddingBottom;
	
	private Integer paddingTop;
	
	private Integer marginBottom;
	
	private Integer fontSize;
	
	private String color;
	
	private Integer flex;
	
	private String textAlign;
	
	public StyleComponent() {
		super();
	}

	public StyleComponent(String fontWeight, int paddingTop, int marginBottom, int fontSize, String color) {
		super();
		this.fontWeight = fontWeight;
		this.paddingTop = paddingTop;
		this.marginBottom = marginBottom;
		this.fontSize = fontSize;
		this.color = color;
	}
	
	public StyleComponent(int flex, String color) {
		this.flex = flex;
		this.color = color;
	}

	public StyleComponent(String fontWeight,int fontSize, String color, int flex, String textAlign) {
		this.fontWeight = fontWeight;
		this.fontSize = fontSize;
		this.color = color;
		this.flex = flex;
		this.textAlign = textAlign;
	}
	
	public StyleComponent(String fontWeight, String color, int flex, String textAlign) {
		this.fontWeight = fontWeight;
		this.color = color;
		this.flex = flex;
		this.textAlign = textAlign;
	}
	
}
