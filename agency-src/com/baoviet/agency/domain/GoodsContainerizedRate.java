package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the GOODS_CONTAINERIZED_RATE database table.
 * 
 */
@Entity
@Table(name="GOODS_CONTAINERIZED_RATE")
@Getter
@Setter
public class GoodsContainerizedRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RATE_ID", unique=true)
	private String rateId;

	@Column
	private Integer goodsCategoryId;
	
	@Column
	private Double rate;

	@Override
	public String toString() {
		return "GoodsContainerizedRate [rateId=" + rateId + ", goodsCategoryId=" + goodsCategoryId + ", rate=" + rate
				+ "]";
	}

}