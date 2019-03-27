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
public class PaymentGiftCodeRequestVM {

	private List<String> agreementIds;
	
    private String giftCode;
}
