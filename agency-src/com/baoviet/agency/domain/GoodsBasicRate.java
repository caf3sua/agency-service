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
 * The persistent class for the GOODS_BASIC_RATE database table.
 * 
 */
@Entity
@Table(name="GOODS_BASIC_RATE")
@Getter
@Setter
public class GoodsBasicRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "GOODS_BASIC_RATE_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="RATE_ID", unique=true)
	private String rateId;

	@Column
	private Integer category;
	
	@Column
	private String categoryName;
	
	@Column
	private Integer packedType;
	
	@Column
	private String packedTypeName;
	
	@Column
	private Integer transport;
	
	@Column
	private String transportName;
	
	@Column(name="OVER_500KM")
	private Integer over500km;
	
	@Column
	private Double rate;

	@Override
	public String toString() {
		return "GoodsBasicRate [rateId=" + rateId + ", category=" + category + ", categoryName=" + categoryName
				+ ", packedType=" + packedType + ", packedTypeName=" + packedTypeName + ", transport=" + transport
				+ ", transportName=" + transportName + ", over500km=" + over500km + ", rate=" + rate + "]";
	}

}