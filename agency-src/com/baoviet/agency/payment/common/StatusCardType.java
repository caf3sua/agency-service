package com.baoviet.agency.payment.common;

import lombok.Getter;

@Getter
public enum StatusCardType {
	NOT_CONFIRMED(0), CONFIRMED(1);
	
	private int value;

	private StatusCardType(int value) {
		this.value = value;
	}
}
