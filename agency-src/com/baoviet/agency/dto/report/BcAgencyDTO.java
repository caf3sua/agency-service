package com.baoviet.agency.dto.report;

import java.io.Serializable;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class BcAgencyDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String datePayment;
	private long totalPremium;

	private long hoaHongSale;
	private long hoaHongTruongDv;
	private long hoaHongDv;
	private long tongHoaHong;
}