package com.baoviet.agency.dto.excel;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductImportDTO extends BasePathInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
}