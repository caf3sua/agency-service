package com.baoviet.agency.web.rest.vm;

import java.util.List;

import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.PaAddDTO;
import com.baoviet.agency.dto.PaDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HastableTNC {

	private PaDTO pa;
	
	private AgreementDTO agreement;
	
	private List<PaAddDTO> lstPaAdd;
}
