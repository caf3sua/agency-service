package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.ProductTncService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.HastableTNC;
import com.baoviet.agency.web.rest.vm.PremiumTncVM;
import com.baoviet.agency.web.rest.vm.ProductTncVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementTNCVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency TNC (tai nan con người) resource.
 * @author Nam, Nguyen Hoai
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/tnc")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/tnc", description = "<a href=\"/content/extra_doc/tnc.html\" target=\"_blank\">External document</a>")
public class ProductTncResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductTncResource.class);

    private static final String ENTITY_NAME = "tnc";

    @Autowired
    private ProductTncService productTncService;
    
    @Autowired
    private AgreementService agreementService;
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TNC_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính phí Bảo hiểm tai nạn con người.")
    public ResponseEntity<PremiumTncVM> getPremium(@Valid @RequestBody PremiumTncVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		// Call service
		PremiumTncVM data = productTncService.calculatePremium(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TNC_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu Bảo hiểm tai nạn con người.")
    public ResponseEntity<HastableTNC> createPolicy(@Valid @RequestBody ProductTncVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createPolicy : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		HastableTNC data = productTncService.createOrUpdatePolicy(param, currentAgency);

		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TNC_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu Bảo hiểm tai nạn con người.")
    public ResponseEntity<HastableTNC> update(@Valid @RequestBody ProductTncVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to update : {}", param);
		
		// validate 
		validateUpdateProduct(param);
				
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		HastableTNC data = productTncService.createOrUpdatePolicy(param, currentAgency);

		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TNC_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu Bảo hiểm tai nạn con người.")
    @Deprecated
    public ResponseEntity<UpdateAgreementTNCVM> updatePolicy(@Valid @RequestBody UpdateAgreementTNCVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		//TvicareBaseVM data = productTvcService.updatePolicy(param);
		Boolean result = agreementService.updateAgreementTNC(param.getHas());
		param.setResult(result);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
}
