package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the SktdRate database table.
 * 
 */
@Entity
@Table(name="PRODUCT_GEN")
@Getter
@Setter
public class ProductGen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRODUCT_GEN_ID")
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "PRODUCT_GEN_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String productGenId;

	@Column
	private String fkProductId;
	
	@Column
	private String parentId;
	
	@Column
	private String status;
	
	@Column
	private String name;
	
	@Column
	private String isGroup;
	
	@Column
	private String urlCart;
	
	@Column
	private String urlImage;
	
	@Column
	private String isHome;
	
	@Column
	private String sort;
	
	@Column
	private String urlBanner;
	
	@Column
	private String urlIco;
	
	@Column
	private String site;
	
}