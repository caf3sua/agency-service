package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectConfirmDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("statusCode")
	private String statusCode;

	@JsonProperty("messages")
	private List<String> messages;
	
	
}
