package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
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
import com.baoviet.agency.service.ProductHomeService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.ProductHomeVM;
import com.baoviet.agency.web.rest.vm.PremiumHomeVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementHomeVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency TVI resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/home")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/home", description = "<a href=\"/content/extra_doc/home.html\" target=\"_blank\">External document</a>")
public class ProductHomeResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductHomeResource.class);

    private static final String ENTITY_NAME = "home";

    @Autowired
	private HttpServletRequest request;
    
    @Autowired
    private ProductHomeService productHomeService;
    

    @Autowired
    private AgreementService agreementService;
        
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_HOM_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính phí Bảo hiểm nhà tư nhân Home.")
    public ResponseEntity<PremiumHomeVM> getPremium(@Valid @RequestBody PremiumHomeVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		// Call service
		PremiumHomeVM data = productHomeService.calculatePremium(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_HOM_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu bảo hiểm nhà tư nhân Home")
    public ResponseEntity<ProductHomeVM> createPolicy(@Valid @RequestBody ProductHomeVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createPolicy : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Get user agent
		String userAgent = request.getHeader("user-agent");
		param.setUserAgent(userAgent);
		
		// Call service
		ProductHomeVM data = productHomeService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_HOM_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu bảo hiểm nhà tư nhân Home")
    public ResponseEntity<ProductHomeVM> update(@Valid @RequestBody ProductHomeVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to update : {}", param);
		// validate 
		validateUpdateProduct(param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Get user agent
		String userAgent = request.getHeader("user-agent");
		param.setUserAgent(userAgent);
		
		// Call service
		ProductHomeVM data = productHomeService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_HOM_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu bảo hiểm nhà tư nhân Home")
    @Deprecated
    public ResponseEntity<UpdateAgreementHomeVM> updatePolicy(@Valid @RequestBody UpdateAgreementHomeVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		boolean result = agreementService.updateAgreementHome(param.getAgreement(), param.getHome());
		param.setResult(result);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
}
