package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	private String poNumber;

	private String poStatus;

	private String poPrice;

	private String poSapPrice;
	
	private ObjProduct products;
	
	private String warehouseId;
	
	private String merchantNote;
	
}
