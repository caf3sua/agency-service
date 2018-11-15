package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.TviCareAddDTO;
import com.baoviet.agency.dto.TvicareDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class UpdateAgreementTviVM {

    @NotNull
    private AgreementDTO agreement;

    @NotNull
    private TvicareDTO tvicare;
    
    private List<TviCareAddDTO> lstTviCareAdd;
    
    private boolean result;
}
