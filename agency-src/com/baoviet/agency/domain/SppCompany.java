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
 * The persistent class for the SPP_COMPANY database table.
 * 
 */
@Entity
@Table(name="SPP_COMPANY")
@Getter
@Setter
public class SppCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COMPANY_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "SPP_COMPANY_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String companyId;
	
	@Column
	private String companyName;
	
	@Column
	private String address;
	
	@Column
	private String phone;
	
	@Column
	private String companyCode;
	
	@Column
	private String tenTat;
	
	@Column
	private String btcCodeTinhthanh;
	
	@Column
	private String btcCodeChinhanh;
	
	@Column
	private String maCongty;
	
	@Column
	private String type;
}