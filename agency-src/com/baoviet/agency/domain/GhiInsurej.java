package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the FILES database table.
 * 
 */
@Entity
@Table(name="GHI_INSUREJ")
@Getter
@Setter
public class GhiInsurej implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LINE_ID", unique=true)
	private String lineId;

	@Column
	private Integer ghiInsurej;

}