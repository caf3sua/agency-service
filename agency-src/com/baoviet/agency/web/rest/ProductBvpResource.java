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
import com.baoviet.agency.service.ProductBVPService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.PremiumBVPVM;
import com.baoviet.agency.web.rest.vm.ProductBvpVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementBVPVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency bvp resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/bvp")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/bvp", description = "<a href=\"/content/extra_doc/bvp.html\" target=\"_blank\">External document</a>")
public class ProductBvpResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductBvpResource.class);

    private static final String ENTITY_NAME = "bvp";

    @Autowired
    private ProductBVPService productBVPService;
    @Autowired
    private AgreementService agreementService;
    
//    {
//    	  "chuongTrinh": "1",
//    	  "ngaySinh": "01/04/1982",
//    	  "tuoi": 36,
//    	  "ngoaitruChk": false,
//    	  "ngoaitruPhi": 0,
//    	  "tncnChk": false,
//    	  "tncnSi": 0,
//    	  "tncnPhi": 0,
//    	  "smcnChk": true,
//    	  "smcnSi": 0,
//    	  "smcnPhi": 0,
//    	  "nhakhoaChk": false,
//    	  "nhakhoaPhi": 0,
//    	  "thaisanChk": false,
//    	  "thaisanPhi": 0,
//    	  "thoihanbhTu": "14/04/2018",
//    	  "qlChinhPhi": 1315600,
//    	  "phiBH": 1315600,
//    	  "premiumNet": 1315600,
//    	  "premiumDiscount": 0,
//    	  "pagencyRole": "string"
//    	}
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_BVP_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính Phí Bảo hiểm sức khỏe toàn diện. Ngày sinh và Từ ngày để định dạng dd/MM/yyyy")
    public ResponseEntity<PremiumBVPVM> getPremium(@Valid @RequestBody PremiumBVPVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		// Call service
		PremiumBVPVM data = productBVPService.calculatePremium(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_BVP_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu Bảo hiểm sức khỏe toàn diện.")
    public ResponseEntity<ProductBvpVM> createPolicy(@Valid @RequestBody ProductBvpVM obj) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createPolicy : {}", obj);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		ProductBvpVM data = productBVPService.createOrUpdatePolicy(obj, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_BVP_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu Bảo hiểm sức khỏe toàn diện.")
    public ResponseEntity<ProductBvpVM> update(@Valid @RequestBody ProductBvpVM obj) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to update : {}", obj);
		
		// validate 
		validateUpdateProduct(obj);
				
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		ProductBvpVM data = productBVPService.createOrUpdatePolicy(obj, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_BVP_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu Bảo hiểm sức khỏe toàn diện.")
    @Deprecated
    public ResponseEntity<UpdateAgreementBVPVM> updatePolicy(@Valid @RequestBody UpdateAgreementBVPVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		Boolean result = agreementService.updateAgreementBVP(param.getAgreement(), param.getBvp());
		param.setResult(result);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
}
