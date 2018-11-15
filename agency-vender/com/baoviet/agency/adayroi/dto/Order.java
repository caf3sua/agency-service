package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String orderCode;

	private Date orderCreatedTime;

	private Date orderUpdateTime;
	
	private Double orderPrice;
	
	private String orderStatus;
	
	private String orderStatusChangedSource;
	
	private DeliveryAddress DeliveryAddress;
	
	private DeliveryAddress paymentAddress;
	
	private CustomerInvoiceDetail CustomerInvoiceDetail;
	
	private ObjPurchaseOrders purchaseOrders;
	
	private Boolean HasChangesFromCs;
	
	private String paymentModeName;
	
	private String jsonToMC;
}
