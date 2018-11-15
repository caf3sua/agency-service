package com.baoviet.agency.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AgreementStatusDTO database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class AgreementSearchDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private AgreementDTO objAgreement;
	
	private Object objProduct;
	
	private List<TviCareAddDTO> lstTviCareAddProduct;
	
	private List<TravelCareAddDTO> lstTravelCareAddProduct;
	
	private List<TinhtrangSkDTO> lstTinhtrangSkAddProduct;
	
	private List<TlAddDTO> lstTlAddProduct;
	
	private List<PaAddDTO> lstPaAddProduct;
}