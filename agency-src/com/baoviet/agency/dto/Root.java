package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
@XmlRootElement
public class Root implements Serializable {
	private static final long serialVersionUID = 1L;

	private String POLICY_NUMBER;
	private String NGUOIYC_NAME;
	private String NGUOIYC_CMND;
	private String NGUOIYC_NGAYSINH;
	private String NGUOIYC_DIACHITHUONGTRU;
	private String NGUOIYC_DIENTHOAI;
	private String NGUOIYC_EMAIL;
	private String BAOVIET_DEPARTMENT_ID;
	private String BAOVIET_DEPARTMENT_NAME;
	private String BAOVIET_NAME;
	private String NGAY_HOA_DON;
	private String CAN_BO_QLDV;
	private String URN_DAILY;
	private String AGENT_NAME;
	private String BAOVIET_COMPANY_NAME;
	private String KENH_PHAN_PHOI;
	private String DIEN_THOAI;

	private List<BvpNdbhObj> NGUOIDBH_COL;

}