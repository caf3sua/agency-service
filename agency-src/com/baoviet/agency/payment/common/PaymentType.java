package com.baoviet.agency.payment.common;

import lombok.Getter;

@Getter
public enum PaymentType {
	l23Pay("123Pay"), Momo("Momo"), ViettelPay("ViettelPay"), VnPay("VnPay"), Common("Common"), ViViet("ViViet"),;
	
	private String value;

	private PaymentType(String value) {
		this.value = value;
	}
}
