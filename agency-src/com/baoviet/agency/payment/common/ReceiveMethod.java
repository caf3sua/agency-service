package com.baoviet.agency.payment.common;

import lombok.Getter;

@Getter
public enum ReceiveMethod {
	SoftCopy("1"), HardCopy("2");

	private String value;

	private ReceiveMethod(String value) {
		this.value = value;
	}
}
