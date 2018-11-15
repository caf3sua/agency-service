package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class UpdateAgreementTNCVM {

    @NotNull
    private HastableTNC has;
    
    private boolean result;
}
