package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.KcareBenefit;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.ProductKcareService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.ProductKcareVM;
import com.baoviet.agency.web.rest.vm.PremiumKcareVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementKcareVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency KCARE resource.
 * @author Nam, Nguyen Hoai
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/kcare")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/kcare", description = "<a href=\"/content/extra_doc/kcare.html\" target=\"_blank\">External document</a>")
public class ProductKcareResource extends AbstractAgencyResource {

    private final Logger log = LoggerFactory.getLogger(ProductKcareResource.class);

    private static final String ENTITY_NAME = "kcare";

    @Autowired
    private ProductKcareService productKcareService;
    
    @Autowired
    private AgreementService agreementService;
        
    @GetMapping("/benefit/all")
    @Timed
    @ApiOperation(value=KCARE_BENEFIT_VALUE, notes= KCARE_BENEFIT_NOTES
    	, response = KcareBenefit.class, responseContainer = "List")
    public ResponseEntity<List<KcareBenefit>> getAllBenefit() throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllBenefit");
	
		// Call service
		List<KcareBenefit> data = productKcareService.getAllBenefit();
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_KCR_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value=KCARE_PREMIUM_VALUE, notes=KCARE_PREMIUM_NOTES, response = PremiumKcareVM.class)
    public ResponseEntity<PremiumKcareVM> getPremium(@Valid @RequestBody PremiumKcareVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremiumKCARE : {}", param);
		
		// Call service
		PremiumKcareVM data = productKcareService.calculatePremium(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_KCR_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value=KCARE_CREATE_POLICY_VALUE, notes=KCARE_CREATE_POLICY_NOTES, response = ProductKcareVM.class)
    public ResponseEntity<ProductKcareVM> createPolicy(@Valid @RequestBody ProductKcareVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createPolicy : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		ProductKcareVM data = productKcareService.createOrUpdatePolicy(currentAgency, param);
		
		log.debug("CreatePolicy Kcare success : {}", data.getPolicyNumber());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_KCR_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value=KCARE_UPDATE_POLICY_VALUE, notes=KCARE_UPDATE_POLICY_NOTES, response = ProductKcareVM.class)
    public ResponseEntity<ProductKcareVM> update(@Valid @RequestBody ProductKcareVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to update : {}", param);
		
		// validate 
		validateUpdateProduct(param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		ProductKcareVM data = productKcareService.createOrUpdatePolicy(currentAgency, param);
		
		log.debug("createOrUpdatePolicy Kcare success : {}", data.getPolicyNumber());
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_KCR_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value=KCARE_UPDATE_POLICY_VALUE, notes=KCARE_UPDATE_POLICY_NOTES, response = UpdateAgreementKcareVM.class)
    @Deprecated
    public ResponseEntity<UpdateAgreementKcareVM> updatePolicy(@Valid @RequestBody UpdateAgreementKcareVM param) throws URISyntaxException, AgencyBusinessException{
    	log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		boolean result = agreementService.updateAgreementKcare(param.getAgreement(), param.getKcare(), param.getLstTinhtrangSKs());
		param.setResult(result);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
}
