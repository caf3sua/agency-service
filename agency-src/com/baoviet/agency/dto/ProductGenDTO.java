package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the SktdRate database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ProductGenDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String productGenId;

	private String fkProductId;
	
	private String parentId;
	
	private String status;
	
	private String name;
	
	private String isGroup;
	
	private String urlCart;
	
	private String urlImage;
	
	private String isHome;
	
	private String sort;
	
	private String urlBanner;
	
	private String urlIco;
	
	private String site;
	
}