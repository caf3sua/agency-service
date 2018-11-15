package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the PA_RATE database table.
 * 
 */
@Entity
@Table(name = "MV_CLA_OUTLET_LOCATION")
@Getter
@Setter
public class MvClaOutletLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OUTLET_ID", unique = true)
	private Integer outletId;

	@Column
	private String outletTypeCode;

	@Column
	private String outletAmsId;

	@Column
	private String outletName;

	@Column
	private Integer prOutletId;

	@Column
	private String prOutletTypeCode;

	@Column
	private String prOutletAmsId;

	@Column
	private String prOutletName;

	@Column
	private Date versionDate;

	@Column
	private Date fromDate;

	@Column
	private Date toDate;

	@Column
	private String createUser;

	@Column
	private String updateUser;

	@Column
	private Date createDatetime;

	@Column
	private Date updateDatetime;

	@Column
	private String urn;

	@Column
	private Date ngayCapCcPnt;

	@Column
	private String maVietTatCongTy;

	@Column
	private String maVietTatCongTyIj;

	@Column
	private String maVietTatCongTyAc;
}