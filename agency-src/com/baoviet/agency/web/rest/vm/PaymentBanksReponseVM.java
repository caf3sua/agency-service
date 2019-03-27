package com.baoviet.agency.web.rest.vm;

import java.util.List;

import com.baoviet.agency.payment.domain.PaymentBank;

import lombok.Getter;
import lombok.Setter;

/**
 * Tư vấn viên
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class PaymentBanksReponseVM {

	List<PaymentBank> paymentBanks;
    
}
