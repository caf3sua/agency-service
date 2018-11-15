package com.baoviet.agency.dto.eclaim;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EclaimResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("status")
	private Integer status;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("data")
	private List<EclaimDTO> data;
}