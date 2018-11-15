/* 
* Copyright 2011 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
*/
package com.baoviet.agency.bean;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/** 
* 
* @author: Nam, Nguyen Hoai 
*/
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class FileContentDTO { 
	private String content;
	
	private String fileType;
	
	private String filename;
	
	private String attachmentId;
}