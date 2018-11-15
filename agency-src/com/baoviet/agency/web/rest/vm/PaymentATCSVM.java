package com.baoviet.agency.web.rest.vm;

import lombok.Getter;
import lombok.Setter;

/**
 * Tư vấn viên
 * @author Duc, Le Minh
 */
@Getter
@Setter
public class PaymentATCSVM {

    private String transactionId;
    
    private String gycbhNumber;
    
    private Integer feePayment;
    
}
