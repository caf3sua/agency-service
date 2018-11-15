package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the TRAVELCARE_BENIFIT database table.
 * 
 */
@Entity
@Table(name="TRAVELCARE_BENIFIT")
@Getter
@Setter
public class TravelcareBenifit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	private String id;

	@Column
	private String eur;

	@Column(name="NAME_EN")
	private String nameEn;

	@Column(name="NAME_VI")
	private String nameVi;

	@Column
	private String no;

	@Column
	private String plan;

	@Column(name="PLAN_NAME_EN")
	private String planNameEn;

	@Column(name="PLAN_NAME_VI")
	private String planNameVi;

	@Column
	private String usd;

	@Column
	private String vnd;
}