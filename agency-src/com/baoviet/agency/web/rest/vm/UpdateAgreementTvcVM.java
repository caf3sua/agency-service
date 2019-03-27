package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.TravelCareAddDTO;
import com.baoviet.agency.dto.TravelcareDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class UpdateAgreementTvcVM {

	@NotNull
	private AgreementDTO agreement;

	@NotNull
	private TravelcareDTO travelcare;

	private List<TravelCareAddDTO> lstTravelCareAdd;

	private boolean result;	
}
