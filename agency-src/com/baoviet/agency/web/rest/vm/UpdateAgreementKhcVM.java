package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.TlAddDTO;
import com.baoviet.agency.dto.TlDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class UpdateAgreementKhcVM {

	@NotNull
	private AgreementDTO agreement;

	@NotNull
	private TlDTO tlKhc;

	private List<TlAddDTO> lstTlAdd;

	private boolean result;	
}
