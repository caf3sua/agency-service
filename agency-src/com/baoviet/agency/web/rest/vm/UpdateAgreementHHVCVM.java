package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.GoodsDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class UpdateAgreementHHVCVM {

    @NotNull
    private AgreementDTO agreement;

    @NotNull
    private GoodsDTO goods;
    
    private boolean result;
}
