package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.MotoDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class UpdateAgreementMotoVM {

    @NotNull
    private AgreementDTO agreement;

    @NotNull
    private MotoDTO moto;
    
    private boolean result;
}
