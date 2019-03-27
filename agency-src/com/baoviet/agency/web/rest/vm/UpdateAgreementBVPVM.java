package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.BvpDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class UpdateAgreementBVPVM {

    @NotNull
    private AgreementDTO agreement;

    @NotNull
    private BvpDTO bvp;
    
    private boolean result;
}
