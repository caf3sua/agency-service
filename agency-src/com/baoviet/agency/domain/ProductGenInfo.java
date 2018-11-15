package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TL_ADD database table.
 * 
 */
@Entity
@Table(name="PRODUCT_GEN_INFO")
@Getter
@Setter
public class ProductGenInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRODUCT_GEN_INFO_ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "PRODUCT_GEN_INFO_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String productGenInfoId;
	
	@Column
	private String fkProductGenId;
	
	@Column
	private String title;
	
	@Column
	private String status;
	
	@Lob
	private byte[] content;
	
	@Column
	private String isDefault;
	
	@Column
	private String isShowMenu;
	
	@Column
	private String urlRedirect;
	
	@Column
	private String sort;
	
	@Column
	private String description;
	
	@Column
	private String fkProductGenIds;
	
	@Column
	private String fkProductGenCatId;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date issueDate;
}