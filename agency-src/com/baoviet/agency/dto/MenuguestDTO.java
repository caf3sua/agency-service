package com.baoviet.agency.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MENUGUEST database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class MenuguestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal isMenu;

	private BigDecimal isPermision;

	private BigDecimal isReport;

	private String menuId;

	private String menuImage;

	private String menuKey;

	private BigDecimal menuLevel;

	private String menuName;

	private String menuOrder;

	private String menuParentId;

	private String menuUrl;

}