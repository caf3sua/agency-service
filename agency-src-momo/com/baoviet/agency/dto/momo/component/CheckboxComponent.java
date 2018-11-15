package com.baoviet.agency.dto.momo.component;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckboxComponent extends BaseComponent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String type = "checkBox";
	
	private boolean value;
	
	@NotEmpty
	private String text;
}
