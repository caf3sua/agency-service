package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryAddress implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String CustomerName;

	private String CustomerPhone;

	private String CustomerEmail;
	
	private Address Address;
}
