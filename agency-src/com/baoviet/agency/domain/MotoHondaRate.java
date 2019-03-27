package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MOTO database table.
 * 
 */
@Entity
@Table(name="MOTO_HONDA_RATE")
@Getter
@Setter
public class MotoHondaRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="SOTIEN_FROM")
	private Double sotienFrom;
	
	@Column(name="SOTIEN_TO")
	private Double sotienTo;
	
	@Id
	@Column(name="SO_NAM")
	private String soNam;
	
	@Column(name="GOI")
	private String goi;
	
	@Column(name="PHI")
	private Double phi;

}