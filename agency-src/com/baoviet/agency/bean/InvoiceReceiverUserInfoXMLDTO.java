/* 
* Copyright 2011 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
*/
package com.baoviet.agency.bean;
import lombok.Getter;
import lombok.Setter;

/** 
* 
* @author: Nam, Nguyen Hoai 
*/
@Getter
@Setter
public class InvoiceReceiverUserInfoXMLDTO { 

	private String INVOICE_BUYER; 
	
	private String INVOICE_COMPANY; 
	
	private String INVOICE_TAX_NO ; 
	
	private String INVOICE_ADDRESS;
	
	private String RECEIVER_NAME;
	private String RECEIVER_MOIBLE;
	private String RECEIVER_ADDRESS;
	
}