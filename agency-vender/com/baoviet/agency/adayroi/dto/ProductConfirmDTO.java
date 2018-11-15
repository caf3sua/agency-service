package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductConfirmDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String originalQuantity;
	
	private String poCode;
	
	private String productCode;
	
	private String originalWarehouseID;
	
	private List<QtyWarehouseConfirmDTO> qtyWarehouseConfirms;
}
