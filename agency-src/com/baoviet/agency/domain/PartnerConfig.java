package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * The persistent class for the PARTNER_CONFIG database table.
 * 
 */
@Entity
@Table(name="PARTNER_CONFIG")
@Getter
@Setter
public class PartnerConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "PARTNER_CONFIG_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;

	@Column
	private BigDecimal commision;

	@Column
	private BigDecimal discount;

	@Column
	private String f1;

	@Column
	private String f2;

	@Column
	private String f3;

	@Column
	private String f4;

	@Column
	private String f5;

	@Column(name="ID_PARTNER")
	private String idPartner;

	@Column(name="PRODUCT_CODE")
	private String productCode;

	@Column(name="PRODUCT_NAME")
	private String productName;

	@Column
	private BigDecimal support;

}