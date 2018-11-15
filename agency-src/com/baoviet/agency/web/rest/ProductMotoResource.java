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
import com.baoviet.agency.service.ProductMotoService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.PremiumMotoVM;
import com.baoviet.agency.web.rest.vm.ProductMotoVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementMotoVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency MOTO resource.
 * @author Nam, Nguyen Hoai
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/moto")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/moto", description = "<a href=\"/content/extra_doc/moto.html\" target=\"_blank\">External document</a>")
public class ProductMotoResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductMotoResource.class);

    private static final String ENTITY_NAME = "moto";

    @Autowired
    private ProductMotoService productMotoService;
    
    @Autowired
    private AgreementService agreementService;
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính phí Bảo hiểm xe máy.")
    public ResponseEntity<PremiumMotoVM> getPremium(@Valid @RequestBody PremiumMotoVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		// Call service
		PremiumMotoVM data = productMotoService.calculatePremium(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu Bảo hiểm xe máy.")
    public ResponseEntity<ProductMotoVM> createPolicy(@Valid @RequestBody ProductMotoVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to createPolicy : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		ProductMotoVM data = productMotoService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu Bảo hiểm xe máy.")
    public ResponseEntity<ProductMotoVM> update(@Valid @RequestBody ProductMotoVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to update : {}", param);
		
		// validate 
		validateUpdateProduct(param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Call service
		ProductMotoVM data = productMotoService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_MOT_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu Bảo hiểm xe máy.")
    @Deprecated
    public ResponseEntity<UpdateAgreementMotoVM> updatePolicy(@Valid @RequestBody UpdateAgreementMotoVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		boolean result = agreementService.updateAgreementMoto(param.getAgreement(), param.getMoto());
		param.setResult(result);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
}
