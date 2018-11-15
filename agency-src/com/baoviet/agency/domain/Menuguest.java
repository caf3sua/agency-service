package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * The persistent class for the MENUGUEST database table.
 * 
 */
@Entity
@Getter
@Setter
public class Menuguest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="IS_MENU")
	private BigDecimal isMenu;

	@Column(name="IS_PERMISION")
	private BigDecimal isPermision;

	@Column(name="IS_REPORT")
	private BigDecimal isReport;

	@Id
	@Column(name="MENU_ID")
	private String menuId;

	@Column(name="MENU_IMAGE")
	private String menuImage;

	@Column(name="MENU_KEY")
	private String menuKey;

	@Column(name="MENU_LEVEL")
	private BigDecimal menuLevel;

	@Column(name="MENU_NAME")
	private String menuName;

	@Column(name="MENU_ORDER")
	private String menuOrder;

	@Column(name="MENU_PARENT_ID")
	private String menuParentId;

	@Column(name="MENU_URL")
	private String menuUrl;

}