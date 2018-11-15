package com.baoviet.agency.payment.common;

import lombok.Getter;

@Getter
public enum ViettelCheckOrderStatus {
	SUCCESSFUL("00"), FAILED_PAY_ACTION_NOT_EXISTED("01"), FAILED_DATA_NOT_VALID("02");

	private String value;

	private ViettelCheckOrderStatus(String value) {
		this.value = value;
	}
}
