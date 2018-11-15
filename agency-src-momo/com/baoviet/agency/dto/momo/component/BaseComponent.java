package com.baoviet.agency.dto.momo.component;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseComponent {
	@NotEmpty
	private String id;
}
