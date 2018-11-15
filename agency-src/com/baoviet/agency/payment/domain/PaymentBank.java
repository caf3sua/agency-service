package com.baoviet.agency.payment.domain;

import java.io.Serializable;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.StoredProcedureParameter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
@AllArgsConstructor
@Entity
@NamedStoredProcedureQueries({
	   @NamedStoredProcedureQuery(name = "get_list_bank_by_pay_code", 
           procedureName = "PAYMENT_BANKPackage.GetListBankByPayCode",
 		   resultSetMappings = {"get_list_bank_by_pay_code_mapping"},
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_PAYMENT_CODE", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_PAYMENT_BANK", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "get_list_bank_by_pay_code_mapping", classes = {
	        @ConstructorResult(targetClass = PaymentBank.class,
	            columns = {
	                @ColumnResult(name="payment_bank_code",type = String.class),
	                @ColumnResult(name="bvbank_code",type = String.class),
	                @ColumnResult(name="bank_code_view",type = String.class),
	                @ColumnResult(name="payment_bank_name",type = String.class),
	                @ColumnResult(name="payment_id",type = Long.class),
	                @ColumnResult(name="payment_name",type = String.class),
	            }
	        )
	})
})
public class PaymentBank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String paymentBankCode;
	
	private String bvBankCode;
	
	private String bankCodeView;
	
	private String paymentBankName;
	
	private Long paymentId;
	
	private String paymentName;

	public PaymentBank(String paymentBankCode, String bvBankCode, String bankCodeView,
			String paymentBankName, Long paymentId, String paymentName) {
		super();
		this.paymentBankCode = paymentBankCode;
		this.bvBankCode = bvBankCode;
		this.bankCodeView = bankCodeView;
		this.paymentBankName = paymentBankName;
		this.paymentId = paymentId;
		this.paymentName = paymentName;
	}
}