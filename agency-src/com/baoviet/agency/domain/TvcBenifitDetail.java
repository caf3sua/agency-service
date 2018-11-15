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
 * The persistent class for the TVC_BENIFIT_DETAIL database table.
 * 
 */
@Entity
@Table(name="TVC_BENIFIT_DETAIL")
@Getter
@Setter
public class TvcBenifitDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TVC_BENIFIT_DETAIL_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;

	@Column
	private String a;

	@Column
	private String a1;

	@Column
	private String a2;

	@Column
	private String b;

	@Column
	private String c;

	@Column
	private String name;

}