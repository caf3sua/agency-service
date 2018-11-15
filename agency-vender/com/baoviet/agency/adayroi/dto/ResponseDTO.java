package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("access_token")
	private String access_token;

	@JsonProperty("token_type")
	private String token_type;

	@JsonProperty("expires_in")
	private Long expires_in;

	@JsonProperty("refresh_token")
	private String refresh_token;
	
	
}
