package com.baoviet.agency.payment.common;

import lombok.Getter;

@Getter
public enum GycbhStatus {
	PENDING("Đang thanh toán"), DONE("Xong");
	
	private String value;

	private GycbhStatus(String value) {
		this.value = value;
	}
}
