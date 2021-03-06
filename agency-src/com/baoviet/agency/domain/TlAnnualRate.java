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
 * The persistent class for the PA_RATE database table.
 * 
 */
@Entity
@Table(name="TL_ANNUAL_RATE")
@Getter
@Setter
public class TlAnnualRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "TL_ANNUAL_RATE_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;

	@Column
	private String conditionId;
	@Column
	private String conditionName;
	@Column
	private Integer ageFrom;
	@Column
	private Integer ageTo;
	@Column
	private Double rate;
	
	@Override
	public String toString() {
		return "TlAnnualRate [id=" + id + ", conditionId=" + conditionId + ", conditionName=" + conditionName
				+ ", ageFrom=" + ageFrom + ", ageTo=" + ageTo + ", rate=" + rate + "]";
	}

}