package com.baoviet.agency.web.rest.vm;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.KcareDTO;
import com.baoviet.agency.dto.TinhtrangSkDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class UpdateAgreementKcareVM {

    @NotNull
    private AgreementDTO agreement;

    @NotNull
    private KcareDTO kcare;
    
    private List<TinhtrangSkDTO> lstTinhtrangSKs;
    
    private boolean result;
}
