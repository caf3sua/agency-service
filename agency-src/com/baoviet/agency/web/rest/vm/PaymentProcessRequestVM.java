package com.baoviet.agency.web.rest.vm;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Tư vấn viên
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class PaymentProcessRequestVM {

	private List<String> agreementIds;
	
    private String paymentType;
    
    private Double paymentFee;
    
    private String couponCode;
    
    private String bankCode;

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("PaymentProcessRequest: \n");
		buffer.append("paymentType : ");
		buffer.append(paymentType);
		buffer.append("\n");
		buffer.append("paymentFee : ");
		buffer.append(paymentFee);
		return buffer.toString();
	}
    
}
