package com.baoviet.agency.service;

import java.net.URISyntaxException;
import java.util.List;

import com.baoviet.agency.dto.TmpMomoCarDTO;
import com.baoviet.agency.dto.momo.component.MomoComponent;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ProductCarVM;
import com.baoviet.agency.web.rest.vm.PremiumCARVM;

/**
 * Service Interface for managing Agency.
 */
public interface MomoCarService {
	List<MomoComponent> buildFormDataStep1() throws URISyntaxException, AgencyBusinessException;
	
	List<MomoComponent> buildFormDataStep2(String requestId, List<MomoComponent> formInput) throws URISyntaxException, AgencyBusinessException;
	
	List<MomoComponent> buildFormDataStep3(String requestId, String status) throws URISyntaxException, AgencyBusinessException;
	
	List<MomoComponent> buildFormDataStepPhi(String requestId) throws URISyntaxException, AgencyBusinessException;
	
	List<MomoComponent> buildFormDataStep4(String requestId, List<MomoComponent> formInput) throws URISyntaxException, AgencyBusinessException;
	
	List<MomoComponent> buildFormDataStepGTGT(List<MomoComponent> formInput);
	
	List<MomoComponent> buildFormDataStep5(String requestId) throws AgencyBusinessException;
	
	boolean saveFormDataStep1(String requestId, List<MomoComponent> form);
	
	boolean saveFormDataStep2(String requestId, List<MomoComponent> form) throws AgencyBusinessException;
	
	boolean saveFormDataStep3(String requestId, List<MomoComponent> form) throws AgencyBusinessException;
	
	boolean saveFormDataStep4(String requestId, List<MomoComponent> form) throws AgencyBusinessException;
	
	boolean saveFormDataStepGTGT(String requestId, List<MomoComponent> form) throws AgencyBusinessException;
	
	ProductCarVM getValueTmpMomoCar(TmpMomoCarDTO dto, PremiumCARVM premiumCar) throws AgencyBusinessException;
	
	PremiumCARVM getValuePremiumCar(TmpMomoCarDTO dto) throws AgencyBusinessException;
	
	boolean validateDataStep1(List<MomoComponent> form) throws AgencyBusinessException;
	
	boolean validateDataStep2(String requestId, List<MomoComponent> form) throws AgencyBusinessException;
	
	boolean validateDataStep3(List<MomoComponent> form);
	
	boolean validateDataStep4(List<MomoComponent> form);
	
	boolean validateDataStepGTGT(List<MomoComponent> form);
}

