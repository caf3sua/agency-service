package com.baoviet.agency.payment.common;

import lombok.Getter;

@Getter
public enum VnPayOrderStatus {
	SUCCESSFUL("00", "Confirm Success")
	, ORDER_NOT_FOUND("01", "Order not found")
	, ORDER_ALREADY_CONFIRMED("02", "Order already confirmed")
	, INVALID_SIGNATURE("97", "Invalid signature");;

	private String code;
	
	private String message;

	private VnPayOrderStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
