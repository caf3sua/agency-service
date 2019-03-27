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
import com.baoviet.agency.service.ProductKhcService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.ProductKhcVM;
import com.baoviet.agency.web.rest.vm.KhcResultVM;
import com.baoviet.agency.web.rest.vm.PremiumKhcVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementKhcVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency KHC resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/khc")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/khc", description = "<a href=\"/content/extra_doc/khc.html\" target=\"_blank\">External document</a>")
public class ProductKhcResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductKhcResource.class);

    private static final String ENTITY_NAME = "khc";

    @Autowired
    private ProductKhcService productKhcService;
    
    @Autowired
    private AgreementService agreementService;
    
    @Autowired
	private HttpServletRequest request;
        
//    {
//    	  "insuranceStartDate": "12/04/2018",
//    	  "numberMonth": 0,
//    	  "numberPerson": 1,
//    	  "premiumDiscount": 0,
//    	  "premiumKhc": 0,
//    	  "premiumKhcList": [
//    	    {
//    	      "dateOfBirth": "01/04/1982",
//    	      "personName": "nguoi 1",
//    	      "premiumPerson": 0
//    	    }
//    	  ],
//    	  "premiumNet": 0,
//    	  "premiumPackage": 30000000
//    	}
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_KHC_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính phí Bảo hiểm kết hợp con người.")
    public ResponseEntity<PremiumKhcVM> getPremium(@Valid @RequestBody PremiumKhcVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		// Call service
		PremiumKhcVM data = productKhcService.calculatePremium(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_KHC_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu Bảo hiểm kết hợp con người.")
    public ResponseEntity<KhcResultVM> createPolicy(@Valid @RequestBody ProductKhcVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createPolicy : {}", param);
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Get user agent
		String userAgent = request.getHeader("user-agent");
		param.setUserAgent(userAgent);
		
		// Call service
		KhcResultVM data = productKhcService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_KHC_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu Bảo hiểm kết hợp con người.")
    public ResponseEntity<KhcResultVM> update(@Valid @RequestBody ProductKhcVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to update : {}", param);
		
		// validate 
		validateUpdateProduct(param);
				
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
				
		// Get user agent
		String userAgent = request.getHeader("user-agent");
		param.setUserAgent(userAgent);
		
		// Call service
		KhcResultVM data = productKhcService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_KHC_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu Bảo hiểm kết hợp con người.")
    @Deprecated
    public ResponseEntity<UpdateAgreementKhcVM> updatePolicy(@Valid @RequestBody UpdateAgreementKhcVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		boolean result = agreementService.updateAgreementKhc(param.getAgreement(), param.getTlKhc(), param.getLstTlAdd());
		param.setResult(result);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
}
