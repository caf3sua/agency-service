package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QtyWarehouseConfirmDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String warehouseIdConfirm;
	
	private String quantityConfirm;
}
