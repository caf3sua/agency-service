package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * The persistent class for the MENU database table.
 * 
 */
@Entity
@Getter
@Setter
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MENU_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "MENU_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String menuId;

	@Column(name="IS_MENU")
	private BigDecimal isMenu;

	@Column(name="IS_PERMISION")
	private BigDecimal isPermision;

	@Column(name="IS_REPORT")
	private BigDecimal isReport;

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

	@Column
	private String site;

}