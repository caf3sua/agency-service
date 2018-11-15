/* 
* Copyright 2011 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
*/
package com.baoviet.agency.bean;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/** 
* 
* @author: Nam, Nguyen Hoai 
*/
@Getter
@Setter
public class QueryResultDTO implements Serializable { 

	private static final long serialVersionUID = 6327465606924768933L;
	
	private long count;
	private long premium;
}