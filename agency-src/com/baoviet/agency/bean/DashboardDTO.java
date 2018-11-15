/* 
* Copyright 2011 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
*/
package com.baoviet.agency.bean;
import java.io.Serializable;
import java.util.List;

import com.baoviet.agency.dto.AgreementDTO;

import lombok.Getter;
import lombok.Setter;

/** 
* 
* @author: Nam, Nguyen Hoai 
*/
@Getter
@Setter
public class DashboardDTO implements Serializable { 

	private static final long serialVersionUID = 6327465606924768933L;
	
	private String formDate;
	private String toDate;
	
	// Đơn hàng trong tuần
	private long totalOrder;
	// Đơn hàng đã thanh toán
	private long numberOrderPaid;
	// Đơn hàng chưa thanh toán
	private long numberOrderNotPaid;
	
	// Tổng số phí bảo hiểm
	private long totalPremmium;
	// Phí bảo hiểm đã thanh toàn
	private long premiumPaid;
	// Phí bảo hiểm chưa thanh toán
	private long premiumNotPaid;

	// Danh sách đơn hàng chờ đại lý giải quyết
	private List<AgreementDTO> waitAgency;
	// Danh sách đơn hàng chờ Bảo Việt giải quyết
	private List<AgreementDTO> waitBaoviet;
}