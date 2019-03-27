package com.baoviet.agency.web.rest.vm;

import java.util.List;

import com.baoviet.agency.dto.AgreementDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class SearchAgreementResultVM {

   List<AgreementDTO> lstData;
}
